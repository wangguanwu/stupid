package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.*;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.core.CliServiceImpl;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.option.RaftOptions;
import com.alipay.sofa.jraft.rpc.RpcServer;
import com.alipay.sofa.jraft.rpc.impl.cli.CliClientServiceImpl;
import com.gw.stupid.common.util.ConvertUtils;
import com.gw.stupid.consistency.Serializer;
import com.gw.stupid.consistency.SerializerFactory;
import com.gw.stupid.consistency.cp.AbstractCpRequestProcessor;
import com.gw.stupid.core.distributed.cp.raft.exception.JRaftDuplicateGroupException;
import com.gw.stupid.core.distributed.cp.raft.exception.JRaftRuntimeException;
import com.gw.stupid.core.distributed.cp.raft.util.RaftExecutors;
import com.gw.stupid.core.distributed.cp.raft.util.RaftConstants;
import com.gw.stupid.core.distributed.cp.raft.util.RaftOptionsBuildUtils;
import com.gw.stupid.core.distributed.cp.raft.util.RaftUtils;
import com.gw.stupid.sys.env.EnvUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.gw.stupid.core.distributed.cp.raft.util.RaftUtils.*;

/**
 * @author guanwu
 * @created on 2022-08-22 17:30:46
 **/

@Slf4j
public class JRaftServer {

    static {
        //设置netty低水位
        System.getProperties().setProperty("bolt.netty.buffer.low.watermark",
                String.valueOf(128 * 1024 * 1024));

        //设置netty高水位
        System.getProperties().setProperty("bolt.netty.buffer.high.watermark",
                String.valueOf(256 * 1024 * 1024));

    }

    private Serializer serializer;

    private Configuration conf;

    private RpcServer rpcServer;

    private String selfIp;

    private Integer selfPort;

    PeerId localPeerId;

    NodeOptions nodeOptions;

    private int rpcTimeoutMs;

    private CliService cliService;
    private CliClientServiceImpl cliClientService;

    private volatile boolean isStarted = false;

    private RaftConfig raftConfig;

    // protocol processors
    Set<AbstractCpRequestProcessor> processors = new ConcurrentSkipListSet<>();

    Map<String, AbstractCpRequestProcessor> raftGroup = new ConcurrentHashMap<>();

    public JRaftServer() {
        this.conf = new Configuration();
    }

    /**
     * 可以参考 https://www.sofastack.tech/projects/sofa-jraft/jraft-user-guide/
     * @param raftConfig
     */
    public void init(RaftConfig raftConfig) {
        this.raftConfig = raftConfig;
        this.serializer = SerializerFactory.getDefault();
        log.info("Initialized JRaftServer... raft config:{}", raftConfig);
        RaftExecutors.init(raftConfig);

        final String selfAddress = raftConfig.getSelfMember();

        String []info = selfAddress.split(":");

        selfIp = info[0];
        selfPort = Integer.parseInt(info[1]);
        localPeerId = PeerId.parsePeer(selfAddress);
        nodeOptions = new NodeOptions();

        //选举超时,默认5s
        int electionTimeoutMS = Math.max(ConvertUtils.toInt(raftConfig.
                        getValue(RaftConstants.RAFT_ELECTION_TIMEOUT_MS), RaftConstants.DEFAULT_RAFT_ELECTION_TIMEOUT_MS),
                RaftConstants.DEFAULT_RAFT_ELECTION_TIMEOUT_MS);

        rpcTimeoutMs = ConvertUtils.toInt(RaftConstants.RAFT_RPC_REQUEST_TIMEOUT_MS,
                RaftConstants.DEFAULT_RAFT_RPC_REQUEST_TIMEOUT_MS);

        nodeOptions.setSharedElectionTimer(true);
        nodeOptions.setSharedVoteTimer(true);
        nodeOptions.setSharedStepDownTimer(true);
        nodeOptions.setSharedSnapshotTimer(true);

        nodeOptions.setElectionTimeoutMs(electionTimeoutMS);

        RaftOptions raftOptions = RaftOptionsBuildUtils.initRaftOptions(raftConfig);
        nodeOptions.setRaftOptions(raftOptions);

        //启动jraft metrics record 功能
        //nodeOptions.setEnableMetrics(true);

        CliOptions cliOptions = new CliOptions();
        //推荐启动Cli Service功能，提供管理Raft Group 的功能: 新增节点，移除节点，改变节点配置列表，重置节点配置功能，转移leader功能
        this.cliService = RaftServiceFactory.createAndInitCliService(cliOptions);
        //客户端的通讯层都依赖 Bolt 的 RpcClient，封装在 CliClientService 接口中
        //可以通过 BoltCliClientService 的 getRpcClient 方法获取底层的 bolt RpcClient 实例以到复用底层通讯组件，实现资源复用
        this.cliClientService = (CliClientServiceImpl) ((CliServiceImpl) this.cliService).getCliClientService();
    }

    public synchronized void start() {

        if (!isStarted) {
            try {
                doStart();
                isStarted = true;
            } catch (Exception e) {
                log.error("Raft 协议启动失败, 原因: ", e.getCause());
                throw new JRaftRuntimeException(e.getMessage(), e);
            }
        }

    }

    private void doStart() {
        log.info("JRaft Server is starting...");
        NodeManager nodeManager = NodeManager.getInstance();
        Set<String> members = raftConfig.getMembers();
        log.info("JRaft集群成员配置: {}", String.join(",",members));
        for (String member : members) {
            PeerId peerId = JRaftUtils.getPeerId(member);
            conf.addPeer(peerId);
            nodeManager.addAddress(peerId.getEndpoint());
        }

        nodeOptions.setInitialConf(conf);

        //初始化rpcServer
        rpcServer = createAndInitRpcServer(this, localPeerId);

        if (!rpcServer.init(null)) {
            log.error("初始化RpcServer失败...");
            throw new RuntimeException("Fail to init the RpcServer.");
        }
        createRaftServiceGroups(this.processors);
        log.info("===========完成启动Raft协议===========");
    }

    private void registerRaftServiceGroup(Collection<AbstractCpRequestProcessor> processors) {
        this.processors.addAll( processors);
        this.createRaftServiceGroups(processors);
    }

    synchronized void createRaftServiceGroups(Collection<AbstractCpRequestProcessor> processors) {

        if (!isStarted) {
            log.info("Raft Server 还没初始化.");
            return;
        }

        String parentPath = Paths.get(EnvUtils.getStupidHome(), "data/protocol/raft").toString();

        for (AbstractCpRequestProcessor processor : processors) {

            final String groupName = processor.group();
            if (raftGroup.containsKey(groupName)) {
                throw new JRaftDuplicateGroupException(String.format("Group name %s exists!", groupName));
            }

            Configuration copyConf = conf.copy();

            NodeOptions copyOpt = nodeOptions.copy();

            //初始化group的工作目录
            RaftUtils.initDirectory(parentPath, groupName, copyOpt);

            //初始化状态机
            StupidStateMachine stupidStateMachine = new StupidStateMachine(this,
                    processor);
            //todo
        }

    }
}
