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
public final class RaftExecutors {

    private static ExecutorService raftCoreExecutor;

    private static ScheduledExecutorService raftCommonExecutor;

    private static ExecutorService raftCliServiceExecutor;

    private static ExecutorService raftSnapshotExecutor;

    private static final String OWNER = ClassUtils.getShortCanonicalName(RaftExecutors.class);

    private volatile static boolean isInited = false;

    private RaftExecutors()  {
    }

    public static void init(RaftConfig raftConfig) {
        if (isInited) {
            return;
        }
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

        isInited = true;
    }

    public static ExecutorService getRaftCoreExecutor() {
        return raftCoreExecutor;
    }

    public static ScheduledExecutorService getRaftCommonExecutor() {
        return raftCommonExecutor;
    }

    public static ExecutorService getRaftCliServiceExecutor() {
        return raftCliServiceExecutor;
    }

    public static ExecutorService getRaftSnapshotExecutor() {
        return raftSnapshotExecutor;
    }

    public static String getOWNER() {
        return OWNER;
    }
}
