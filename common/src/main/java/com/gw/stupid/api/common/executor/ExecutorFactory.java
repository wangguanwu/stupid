package com.gw.stupid.api.common.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * @author guanwu
 * @created on 2022-08-03 15:26:39
 **/
public interface ExecutorFactory {

    ExecutorService newSingleExecutorService(ThreadFactory factory);

    ScheduledExecutorService newSingleScheduledExecutorService(ThreadFactory factory);

    ExecutorService newFixedExecutorService(int nThreads, ThreadFactory factory);

    ScheduledExecutorService newScheduledExecutorService(int nThreads, ThreadFactory factory);
}
