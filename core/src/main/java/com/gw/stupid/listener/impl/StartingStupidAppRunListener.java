package com.gw.stupid.listener.impl;

import com.gw.stupid.common.constants.CommonConstants;
import com.gw.stupid.common.executors.ExecutorUtils;
import com.gw.stupid.common.executors.NamedThreadFactory;
import com.gw.stupid.common.executors.ThreadPoolManager;
import com.gw.stupid.enums.ApiErrorCodeEnum;
import com.gw.stupid.env.EnvUtils;
import com.gw.stupid.exception.runtime.StupidRuntimeException;
import com.gw.stupid.file.AbstractSortableFileChangeListener;
import com.gw.stupid.file.FileChangeEvent;
import com.gw.stupid.file.FileMonitorCenter;
import com.gw.stupid.listener.StupidApplicationRunListener;
import com.gw.stupid.util.FileSystemUtils;
import com.gw.stupid.util.InetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 注册中心监听器的实现
 *
 * @author guanwu
 * @created on 2022-08-01 19:27:10
 **/


@Slf4j
public class StartingStupidAppRunListener implements StupidApplicationRunListener {

    private static final String MODE_PROPERTY_KEY_STAND_MODE = "stupid.mode";

    private static final String LOCAL_IP_PROPERTY_KEY = "stupid.local.ip";

    private static final String STUPID_APPLICATION_CONF = "stupid_application_conf";

    private static final String STUPID_APPLICATION_VERSION = "stupid.app.version";

    private static final Map<String, Object> PROPERTY_MAP = new ConcurrentHashMap<>();

    private volatile boolean starting = false;

    private ScheduledExecutorService logStartingScheduledService = null;

    @Override
    public void starting() {
        starting = true;
        log.info("Stupid is starting...");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        this.starting = false;
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment configurableEnvironment) {
        //创建应用工作目录 conf data logs
        createWorkDirectory();

        //初始化EnvUtils环境属性
        initializeEnvironment(configurableEnvironment);

        // 加载属性到本地存储
        preLoadProperties(configurableEnvironment);

        //初始化系统属性
        initializeSystemProperties(configurableEnvironment);
    }

    private void initializeSystemProperties(ConfigurableEnvironment configurableEnvironment) {
        if (EnvUtils.isStandalone()) {
            System.setProperty(MODE_PROPERTY_KEY_STAND_MODE, "stand alone");
        } else {
            System.setProperty(MODE_PROPERTY_KEY_STAND_MODE, "cluster");
        }
        System.setProperty(STUPID_APPLICATION_VERSION, EnvUtils.getProperty("app.version"));
        System.setProperty(LOCAL_IP_PROPERTY_KEY, InetUtils.getCurrentPeerIp());
    }

    private void preLoadProperties(ConfigurableEnvironment env) {

        try {

            Resource appConfigFileResource = EnvUtils.getAppConfigFileResource();

            Map<String, ?> stringMap = EnvUtils.loadProperties(appConfigFileResource);

            PROPERTY_MAP.putAll(stringMap);

            env.getPropertySources().addLast(
                    new OriginTrackedMapPropertySource(STUPID_APPLICATION_CONF, PROPERTY_MAP));

            registerWatcher();

        } catch (Exception e) {
            throw new StupidRuntimeException(ApiErrorCodeEnum.INTERNAL_ERROR.code, e);
        }
    }

    /**
     * 注册监听器，监听应用配置文件是否变更
     */
    private void registerWatcher() {

        boolean ok = FileMonitorCenter.registerFileWatchListener(EnvUtils.getConfPath(), new AbstractSortableFileChangeListener() {
            @Override
            public void onChange(FileChangeEvent event) {
                try {
                    Map<String, ?> propertyMap = EnvUtils.loadProperties(EnvUtils.getAppConfigFileResource());
                    PROPERTY_MAP.putAll(propertyMap);
                } catch (IOException e) {
                    log.error("Failed to monitor file", e);
                }
            }

            @Override
            public boolean interest(String path) {
                return StringUtils.contains(path, "application.properties");
            }
        });
        if (!ok) {
            log.warn("Register watcher failed!");
        }
    }

    /**
     * 初始化EnvUtils环境
     * @param configurableEnvironment
     */
    private void initializeEnvironment(ConfigurableEnvironment configurableEnvironment) {
        EnvUtils.setEnvironment(configurableEnvironment);
    }

    private void createWorkDirectory() {
        String[] dirs = new String[]{"logs", "data", "conf"};
        for (String dir : dirs) {
            log.info("Stupid work directory:{}", Paths.get(EnvUtils.getStupidHome(), dir));
            try {
                FileSystemUtils.forceMakeDir(dir);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void logClusterConf() {
        if (EnvUtils.isStandalone()) {
            return;
        }
        log.info("Stupid cluster config ip list: {} ", String.join(EnvUtils.getClusterConfig(), CommonConstants.COMMA_DELIMIETER));
    }

    private void logStarting() {
        if (!EnvUtils.isStandalone()) {
            this.logStartingScheduledService = ExecutorUtils.newSingleScheduleExecutor(
                    new NamedThreadFactory("stupid-starting-logger-task"));
            this.logStartingScheduledService.scheduleWithFixedDelay(() -> {
                if (StartingStupidAppRunListener.this.starting) {
                    log.info("Stupid is starting...");
                }
            }, 1, 1, TimeUnit.SECONDS);
        }
    }


    @Override
    public void contextPrepared(ConfigurableApplicationContext configurableApplicationContext) {
        logClusterConf();

        logStarting();
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("Stupid context loaded...");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("Stupid App is running...");

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable er) {
        starting = false;

        createWorkDirectory();

        ThreadPoolManager.shutdown();

        FileMonitorCenter.shutdown();

        //todo NotifyCenter.shutdown()

        closeLoggingStartExecutor();

        log.error("Stupid startup to fail. Please see {} for more details",
                Paths.get(EnvUtils.getStupidHome(), "logs/stupid.log"));
    }

    private void closeLoggingStartExecutor() {
        if (Objects.nonNull(logStartingScheduledService) &&
                !(logStartingScheduledService.isShutdown() || logStartingScheduledService.isTerminated())) {
            logStartingScheduledService.shutdownNow();
        }
    }
}
