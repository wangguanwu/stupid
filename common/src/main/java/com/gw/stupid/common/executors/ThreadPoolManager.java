package com.gw.stupid.common.executors;

import com.gw.stupid.common.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 线程池管理器
 *
 * @author guanwu
 * @created on 2022-08-03 16:29:51
 **/

@Slf4j
public class ThreadPoolManager {

    private ConcurrentHashMap<String, Set<ExecutorService>> executorManager;

    private volatile AtomicBoolean closed = new AtomicBoolean(false);

    @Slf4j
    static class ThreadPoolManagerInstanceHolder {
        public final static ThreadPoolManager INSTANCE = new ThreadPoolManager();
        static {
            INSTANCE.init();
        }
    }

    public static ThreadPoolManager getInstance() {
        return ThreadPoolManagerInstanceHolder.INSTANCE;
    }

    private ThreadPoolManager() {
    }

    private void init() {
        this.executorManager = new ConcurrentHashMap<>(16);
    }

    /**
     * 注册线程池
     * @param group
     * @param executorService
     */
    public void register(String group, ExecutorService executorService) {

        executorManager.computeIfAbsent(group, (g) -> new HashSet<>());

        Set<ExecutorService> executorServices = executorManager.get(group);

        synchronized (executorServices) {
            executorServices.add(executorService);
        }
    }

    public void deregister(String group) {
        executorManager.remove(group);
        log.warn("ThreadPoolManager remove executor group:{}", group);
    }

    public static void registerExecutor(String group, ExecutorService executorService) {
        getInstance().register(group, executorService);
    }

    public static void deregisterExecutor(String group, ExecutorService executorService) {
        getInstance().deregister(group);
    }

    public void shutdownServices() {
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        log.info("ThreadPoolManager shutdown  executor service...");
        for (Map.Entry<String, Set<ExecutorService>> entry : executorManager.entrySet()) {
            Set<ExecutorService> executorSet = entry.getValue();
            for (ExecutorService executorService : executorSet) {
                ThreadPoolUtils.shutdown(executorService);
            }
        }
        executorManager.clear();
        executorManager = null;
    }

    public static void shutdown() {
        getInstance().shutdownServices();
    }

}
