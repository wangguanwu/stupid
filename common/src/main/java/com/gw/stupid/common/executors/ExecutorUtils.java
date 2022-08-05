package com.gw.stupid.common.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * @author guanwu
 * @created on 2022-08-03 16:27:49
 **/
public final class ExecutorUtils {

    private static ExecutorFactory executorFactory;

    private static final ExecutorFactory DEFAULT_FACTORY = JdkExecutorFactory.getInstance();

    public static ExecutorService newSingleExecutor(ThreadFactory threadFactory) {
        return DEFAULT_FACTORY.newSingleExecutorService(threadFactory);
    }

    public static ScheduledExecutorService newSingleScheduleExecutor(ThreadFactory threadFactory) {
        return DEFAULT_FACTORY.newSingleScheduledExecutorService(threadFactory);
    }

    public static ScheduledExecutorService newFixedScheduleExecutor(int nThreads, ThreadFactory threadFactory) {
        return DEFAULT_FACTORY.newScheduledExecutorService(nThreads, threadFactory);
    }

    public static ExecutorService newExecutor(int nThreads, ThreadFactory threadFactory) {
        return DEFAULT_FACTORY.newFixedExecutorService(nThreads, threadFactory);
    }

    public static ExecutorService newManagedFixedSchedule(String group, int nThreads, ThreadFactory threadFactory) {
        ExecutorService executorService = DEFAULT_FACTORY.newFixedExecutorService(nThreads, threadFactory);
        ThreadPoolManager.registerExecutor(group, executorService);
        return executorService;
    }

    public static ExecutorService newManagedSingleFixedSchedule(String group, ThreadFactory threadFactory) {
        ExecutorService singleExecutor = DEFAULT_FACTORY.newSingleExecutorService(threadFactory);
        ThreadPoolManager.registerExecutor(group, singleExecutor);
        return singleExecutor;
    }
}
