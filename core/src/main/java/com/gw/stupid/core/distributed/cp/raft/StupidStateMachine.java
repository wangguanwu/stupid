package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.entity.LeaderChangeContext;
import com.alipay.sofa.jraft.error.RaftException;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.gw.stupid.consistency.CpRequestProcessor;
import com.gw.stupid.consistency.cp.AbstractCpRequestProcessor;
import com.gw.stupid.consistency.snapshot.SnapshotOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JRaft 状态机的实现
 * @author guanwu
 * @created on 2022-09-02 16:15:29
 **/
public class StupidStateMachine extends StateMachineAdapter {


    private final CpRequestProcessor requestProcessor;

    private final JRaftServer jRaftServer;

    private final AtomicBoolean isLeader= new AtomicBoolean(false);

    private final String groupId;

    private Node node;

    private List<RaftSnapshotService> snapshotServices;

    private final ReentrantLock lock = new ReentrantLock();


    public StupidStateMachine(JRaftServer jRaftServer, AbstractCpRequestProcessor requestProcessor) {
        this.jRaftServer = jRaftServer;
        this.requestProcessor = requestProcessor;
        this.groupId = requestProcessor.group();
        List<RaftSnapshotService> list = getSnapshotAdapterList(requestProcessor.getSnapShotOperation());
        registerSnapShotServices(list);

    }

    /**
     * 适配snapshot接口实现类
     * @param snapshotOperations
     * @return
     */
    private List<RaftSnapshotService> getSnapshotAdapterList(List<SnapshotOperation> snapshotOperations) {

        List<RaftSnapshotService> res = new ArrayList<>();
        //todo
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
     * @param iter
     */
    @Override
    public void onApply(Iterator iter) {

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
