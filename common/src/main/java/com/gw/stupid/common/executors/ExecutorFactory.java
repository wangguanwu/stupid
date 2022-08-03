package com.gw.stupid.common.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * @author guanwu
 * @created on 2022-08-03 15:26:39
 **/
public interface ExecutorFactory {

    ExecutorService newSingleExecutorService(ThreadFactory factory);

    ExecutorService newSingleScheduledExecutorService(ThreadFactory factory);

    ExecutorService newFixedExecutorService(int nThreads, ThreadFactory factory);

    ExecutorService newScheduledExecutorService(int nThreads, ThreadFactory factory);
}
