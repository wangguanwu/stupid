package com.gw.stupid.file;

import com.gw.stupid.enums.ApiErrorCodeEnum;
import com.gw.stupid.exception.runtime.StupidRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 文件监控中心，主要监听文件的添加、修改和删除等
 *
 * @author guanwu
 * @created on 2022-08-03 14:18:02
 **/


@Slf4j
public class FileMonitorCenter {

    private final static FileSystem FILE_SYSTEM = FileSystems.getDefault();

    private static final Map<String, Object> LISTENER_MANAGERS  = new ConcurrentHashMap<>(16);


    private static class DirMonitorTask implements Runnable {

        private ExecutorService executorService;

        private WatchService watchService;

        private String monitorPaths;

        private volatile boolean isMonitor = true;

        private Set<FileChangeListener> listeners = new ConcurrentSkipListSet<>();

        public DirMonitorTask(String monitorPaths) {
            this.monitorPaths = monitorPaths;
            Path path = Paths.get(monitorPaths);
            if (!path.toFile().isDirectory()) {
                throw new IllegalArgumentException("Must be a file directory: " + monitorPaths);
            }

            try {
                this.watchService = FILE_SYSTEM.newWatchService();
                this.watchService
            } catch (Throwable e) {
                throw new StupidRuntimeException(ApiErrorCodeEnum.INTERNAL_ERROR.code, e);
            }

        }

        @Override
        public void run() {

        }
    }

}
