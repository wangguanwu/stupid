package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.entity.LeaderChangeContext;
import com.alipay.sofa.jraft.error.RaftError;
import com.alipay.sofa.jraft.error.RaftException;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.google.protobuf.Message;
import com.gw.stupid.api.util.ExceptionUtils;
import com.gw.stupid.consistency.CpRequestProcessor;
import com.gw.stupid.consistency.cp.AbstractCpRequestProcessor;
import com.gw.stupid.consistency.entity.auto.ReadRequest;
import com.gw.stupid.consistency.entity.auto.Response;
import com.gw.stupid.consistency.entity.auto.WriteRequest;
import com.gw.stupid.consistency.snapshot.SnapshotOperation;
import com.gw.stupid.consistency.util.ProtoMessageUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JRaft 状态机的实现
 *
 * @author guanwu
 * @created on 2022-09-02 16:15:29
 **/

@Slf4j
public class StupidStateMachine extends StateMachineAdapter {


    private final CpRequestProcessor requestProcessor;

    private final JRaftServer jRaftServer;

    private final AtomicBoolean isLeader = new AtomicBoolean(false);

    private final String groupId;

    private Node node;

    private List<RaftSnapshotService> snapshotServices;

    private final ReentrantLock lock = new ReentrantLock();

    public void setNode(Node node) {
        this.node = node;
    }

    public StupidStateMachine(JRaftServer jRaftServer, AbstractCpRequestProcessor requestProcessor) {
        this.jRaftServer = jRaftServer;
        this.requestProcessor = requestProcessor;
        this.groupId = requestProcessor.group();
        List<RaftSnapshotService> list = getSnapshotAdapterList(requestProcessor.getSnapShotOperation());
        registerSnapShotServices(list);
    }

    /**
     * 适配snapshot接口实现类
     *
     * @param snapshotOperations
     * @return
     */
    private List<RaftSnapshotService> getSnapshotAdapterList(List<SnapshotOperation> snapshotOperations) {

        List<RaftSnapshotService> res = new ArrayList<>();
        for (SnapshotOperation snapshotOperation : snapshotOperations) {
            res.add(new RaftSnapShotAdapter(snapshotOperation));
        }
        return res;
    }


    public void registerSnapShotServices(List<RaftSnapshotService> raftSnapshotServices) {
        lock.lock();
        try {
            this.snapshotServices.addAll(raftSnapshotServices);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 二阶段提交成功后，会触发onApply方法
     *
     * @param iter
     */
    @Override
    public void onApply(Iterator iter) {
        int index = 0, applied = 0;
        Message message;
        StupidClosure closure = null;
        try {
            while (iter.hasNext()) {
                Status status = Status.OK();
                try {
                    if (iter.done() != null) {
                        //done不为空，说明当前node是leader,可以直接获取closure
                        closure = (StupidClosure) iter.done();
                        message = closure.getMessage();
                    } else {
                        // Closure为null，说明当前node是follower,需要从当前data解析出当前请求类型
                        message = parseFromData(iter.getData());

                        //Follower节点读请求不会改变状态机的状态，跳过即可
                        if (message instanceof ReadRequest) {
                            applied++;
                            index++;
                            iter.next();
                            continue;
                        }
                    }
                    if (message instanceof WriteRequest) {
                        //master节点处理写请求
                        Response response = requestProcessor.onApply((WriteRequest) message);
                        requestFinished(response, closure);
                    } else if (message instanceof ReadRequest) {
                        //master节点处理读请求
                        Response response = requestProcessor.request((ReadRequest) message);
                        requestFinished(response, closure);
                    }
                } catch (Throwable ex) {
                    index++;
                    status.setError(RaftError.UNKNOWN, ExceptionUtils.getStackTrace(ex));
                    if (null != closure) {
                        closure.setThrowable(ex);
                    }
                    throw ex;
                } finally {
                    if (null != closure) {
                        closure.run(status);
                    }
                }

                applied++;
                index++;
                iter.next();
            }

        } catch (Throwable ex) {
            log.error("StupidStatusMachine run onApply has error:{}", ex.getMessage());
            iter.setErrorAndRollback(index - applied, new Status(RaftError.ESTATEMACHINE,
                    "StupidStatusMachine run onApply has error:%s",
                    ExceptionUtils.getStackTrace(ex)));
        }

    }

    private void requestFinished(Response response, StupidClosure done) {
        if (null != response) {
            done.setResponse(response);
        }
    }

    private Message parseFromData(ByteBuffer data) {
        return ProtoMessageUtils.parseFrom(data.array());
    }


    @Override
    public void onShutdown() {
        super.onShutdown();
    }

    @Override
    public void onSnapshotSave(SnapshotWriter writer, Closure done) {
        super.onSnapshotSave(writer, done);
    }

    @Override
    public boolean onSnapshotLoad(SnapshotReader reader) {
        return super.onSnapshotLoad(reader);
    }

    @Override
    public void onLeaderStart(long term) {
        super.onLeaderStart(term);
    }

    @Override
    public void onLeaderStop(Status status) {
        super.onLeaderStop(status);
    }

    @Override
    public void onError(RaftException e) {
        super.onError(e);
    }

    @Override
    public void onConfigurationCommitted(Configuration conf) {
        super.onConfigurationCommitted(conf);
    }

    @Override
    public void onStopFollowing(LeaderChangeContext ctx) {
        super.onStopFollowing(ctx);
    }

    @Override
    public void onStartFollowing(LeaderChangeContext ctx) {
        super.onStartFollowing(ctx);
    }

}
