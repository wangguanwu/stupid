package com.gw.stupid.file;

import com.gw.stupid.common.executors.ExecutorUtils;
import com.gw.stupid.common.executors.NamedThreadFactory;
import com.gw.stupid.enums.ApiErrorCodeEnum;
import com.gw.stupid.exception.runtime.StupidRuntimeException;
import com.gw.stupid.util.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文件监控中心，主要监听文件的添加、修改和删除等
 *
 * @author guanwu
 * @created on 2022-08-03 14:18:02
 **/


@Slf4j
public class FileMonitorCenter {

    private final static FileSystem FILE_SYSTEM = FileSystems.getDefault();

    private static final Map<String, DirMonitorTask> LISTENER_MANAGERS  = new ConcurrentHashMap<>(16);

    private static final AtomicBoolean CLOSED = new AtomicBoolean(Boolean.FALSE);

    private static final int MAX_MONITOR_JOBS = Integer.getInteger("stupid.monitor.file.max-dirs", 32);

    private static int CURRENT_MONITOR_DIRS_COUNT = 0;

    static {
        ThreadUtils.addShutdownHook(FileMonitorCenter::shutdown);
    }

    public synchronized static boolean registerFileWatchListener(String paths, FileChangeListener listener){
        checkState();
        if (CURRENT_MONITOR_DIRS_COUNT == MAX_MONITOR_JOBS) {
            return false;
        }

        DirMonitorTask dirMonitorTask = LISTENER_MANAGERS.get(paths);

        if (dirMonitorTask == null) {
            dirMonitorTask = new DirMonitorTask(paths);
            dirMonitorTask.start();
            LISTENER_MANAGERS.put(paths, dirMonitorTask);
            CURRENT_MONITOR_DIRS_COUNT ++;
        }
        dirMonitorTask.registerWatcher(listener);
        return true;
    }

    public static void shutdown() {

        if (!CLOSED.compareAndSet(false, true)) {
            return;
        }
        log.info("[FileMonitorCenter] starting close...");
        for (Map.Entry<String, DirMonitorTask> taskEntry : LISTENER_MANAGERS.entrySet()) {
            try {
                taskEntry.getValue().shutdown();
            } catch (Throwable tr) {
                log.error("[FileMonitorCenter] shutdown has error ", tr);
            }
        }
        LISTENER_MANAGERS.clear();
        CURRENT_MONITOR_DIRS_COUNT = 0;
        log.info("[FileMonitorCenter] closed.");
    }


    private static class DirMonitorTask extends Thread {

        private ExecutorService executorService;

        private WatchService watchService;

        private String monitorPaths;

        private volatile boolean isMonitor = true;

        private Set<FileChangeListener> listeners = new ConcurrentSkipListSet<>();

        public DirMonitorTask(String monitorPaths) {
            this.monitorPaths = monitorPaths;
            super.setName("file-watch-task-" + monitorPaths);

            Path path = Paths.get(monitorPaths);
            if (!path.toFile().isDirectory()) {
                throw new IllegalArgumentException("Must be a file directory: " + monitorPaths);
            }

            this.executorService = ExecutorUtils.
                    newSingleExecutor(new NamedThreadFactory("stupid-monitor-file-" + monitorPaths));

            try {
                WatchService fileWatcher = FILE_SYSTEM.newWatchService();

                path.register(fileWatcher, StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.OVERFLOW);

                this.watchService = fileWatcher;
            } catch (Throwable e) {
                throw new StupidRuntimeException(ApiErrorCodeEnum.INTERNAL_ERROR.code, e);
            }
        }

        private void doProcessEvents(List<WatchEvent<?>> events) {
            Runnable r = () -> {
                for (WatchEvent<?> event : events) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (StandardWatchEventKinds.OVERFLOW == kind) {
                        try {
                            processOverFlowEvent();
                        } catch (Throwable tr) {
                            log.error("File change event: OVERFLOW", tr);
                        }
                    } else {
                        processEvents(event.context());
                    }
                }
            };
            this.executorService.execute(r);
        }

        private void processOverFlowEvent() {
            File dir = Paths.get(monitorPaths).toFile();

            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isDirectory()) {
                    continue;
                }

                processEvents(file.getName());
            }
        }

        private void processEvents(Object context) {
            final FileChangeEvent fileChangeEvent = new FileChangeEvent(context, this.monitorPaths);
            String contextStr = String.valueOf(context);
            for (FileChangeListener listener : this.listeners) {
                if (!listener.interest(contextStr)) {
                    continue;
                }
                Executor executor = listener.getExecutor();
                Runnable job = () -> listener.onChange(fileChangeEvent);
                if (null != executor) {
                    executor.execute(job);
                } else {
                    try {
                        job.run();
                    } catch (Throwable tr) {
                        log.error("File change event process error:{}", tr.getMessage(), tr);
                    }
                }
            }
        }

        public void registerWatcher(FileChangeListener watcher) {
            this.listeners.add(watcher);
        }

        @Override
        public void run() {
            while(isMonitor) {
                try {
                    WatchKey watchKey = watchService.take();
                    List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                    watchKey.reset();
                    doProcessEvents(watchEvents);
                } catch (InterruptedException e) {
                    log.error("[DirMonitorTask] process event has error", e);
                }
            }

        }

        public void shutdown() {
            this.isMonitor = false;
        }
    }

    private static void checkState() {
        if (CLOSED.get()) {
            throw new IllegalArgumentException("FileMonitorCenter already closed");
        }
    }
}
