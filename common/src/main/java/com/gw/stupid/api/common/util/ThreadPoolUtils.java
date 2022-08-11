package com.gw.stupid.api.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author guanwu
 * @created on 2022-08-05 11:02:18
 * 线程池工具类
 **/

@Slf4j
public class ThreadPoolUtils {

    public static void shutdown(ExecutorService executor) {
        executor.shutdown();
        int retry = 3;
        while(retry-- > 0) {
            try {
                if (executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    return;
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.interrupted();
            } catch (Throwable ex) {
                log.info("ThreadPoolUtils shutdown executor has error", ex);
            }
        }
        executor.shutdownNow();
    }

    public static void addShutdownHook(Runnable r) {
        Runtime.getRuntime().addShutdownHook(new Thread(r));
    }
}
