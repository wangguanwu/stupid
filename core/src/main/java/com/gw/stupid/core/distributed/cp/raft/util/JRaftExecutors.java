package com.gw.stupid.core.distributed.cp.raft.util;

import com.gw.stupid.api.common.executor.ExecutorUtils;
import com.gw.stupid.api.common.executor.NamedThreadFactory;
import com.gw.stupid.core.distributed.cp.raft.RaftConfig;
import org.apache.commons.lang3.ClassUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author guanwu
 * @created on 2022-08-22 19:08:25
 **/
public final class JRaftExecutors {

    private static ExecutorService raftCoreExecutor;

    private static ScheduledExecutorService raftCommonExecutor;

    private static ExecutorService raftCliServiceExecutor;

    private static ExecutorService raftSnapshotExecutor;

    private static final String OWNER = ClassUtils.getShortCanonicalName(JRaftExecutors.class);

    private JRaftExecutors()  {
    }

    public static void init(RaftConfig raftConfig) {
        int coreThreadNum = Integer.parseInt(raftConfig.getOrDefault(RaftConstants.RAFT_CORE_THREAD_NUM,
                "8"));

        int cliServiceThreadNum = Integer.parseInt(raftConfig.getOrDefault
                (RaftConstants.RAFT_CORE_THREAD_NUM, "4"));

        raftCoreExecutor = ExecutorUtils.newManagedFixedExecutor(OWNER, coreThreadNum,
                new NamedThreadFactory("com.gw.stupid.core.raft-core"));

        raftCliServiceExecutor = ExecutorUtils.newManagedFixedExecutor(OWNER, cliServiceThreadNum,
                new NamedThreadFactory("com.gw.stupid.core.raft-cli-service"));

        raftCommonExecutor = ExecutorUtils.newManagedScheduledExecutor(OWNER, 8,
                new NamedThreadFactory("com.gw.stupid.core.protocol.raft-common"));

        int snapshotNum = coreThreadNum >> 1;
        if (snapshotNum == 0) {
            snapshotNum = coreThreadNum;
        }

        raftSnapshotExecutor = ExecutorUtils.newManagedFixedExecutor(OWNER, snapshotNum,
                new NamedThreadFactory("com.gw.stupid.core.raft-snapshot"));
    }

}
