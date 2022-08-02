package com.gw.stupid.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * 应用工具
 *
 * @author guanwu
 * @created on 2022-08-01 16:43:02
 **/

@Slf4j
public class ApplicationUtils implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.info("初始化自定义ApplicationUtils...");
        initApplicationContext(configurableApplicationContext);
        log.info("初始化完毕ApplicationUtils...");

    }

    public static void initApplicationContext(ConfigurableApplicationContext applicationContext) {
        ApplicationUtils.applicationContext = applicationContext;
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }
}
