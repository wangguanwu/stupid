package com.gw.stupid.common.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author guanwu
 * @created on 2022-08-03 16:13:36
 **/
public class JdkExecutorFactory implements ExecutorFactory {

    static class JdkExecutorFactoryInner {
       public static final JdkExecutorFactory INSTANCE = new JdkExecutorFactory();
    }

    private JdkExecutorFactory() {
    }

    public static JdkExecutorFactory getInstance() {
        return JdkExecutorFactoryInner.INSTANCE;
    }

    @Override
    public ExecutorService newSingleExecutorService(ThreadFactory factory) {
        return Executors.newSingleThreadScheduledExecutor(factory);
    }

    @Override
    public ExecutorService newSingleScheduledExecutorService(ThreadFactory factory) {
        return Executors.newSingleThreadScheduledExecutor(factory);
    }

    @Override
    public ExecutorService newFixedExecutorService(int nThreads, ThreadFactory factory) {
        return Executors.newFixedThreadPool(nThreads, factory);
    }

    @Override
    public ExecutorService newScheduledExecutorService(int nThreads, ThreadFactory factory) {
        return Executors.newScheduledThreadPool(nThreads, factory);
    }


}
