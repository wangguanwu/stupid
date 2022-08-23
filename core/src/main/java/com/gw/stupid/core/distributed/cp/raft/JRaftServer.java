package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.option.RaftOptions;
import com.alipay.sofa.jraft.rpc.RpcServer;
import com.gw.stupid.api.common.util.ConvertUtils;
import com.gw.stupid.consistency.Serializer;
import com.gw.stupid.consistency.SerializerFactory;
import com.gw.stupid.core.distributed.cp.raft.util.JRaftExecutors;
import com.gw.stupid.core.distributed.cp.raft.util.RaftConstants;
import com.gw.stupid.core.distributed.cp.raft.util.RaftOptionsBuilder;
import lombok.extern.slf4j.Slf4j;

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

    public JRaftServer() {
        this.conf = new Configuration();

    }

    public void init(RaftConfig raftConfig) {
        this.serializer = SerializerFactory.getDefault();
        log.info("Initialized JRaftServer... raft config:{}", raftConfig);
        JRaftExecutors.init(raftConfig);

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

        RaftOptions raftOptions = RaftOptionsBuilder.initRaftOptions(raftConfig);






    }
}
