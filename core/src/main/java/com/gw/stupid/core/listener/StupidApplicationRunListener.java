package com.gw.stupid.core.listener;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * stupid注册中心监听器
 * spring初始化调用SpringApplicationRunListener时，会调用，执行一些stupid的核心方法
 *
 * @author guanwu
 * @created on 2022-08-01 19:17:24
 **/
public interface StupidApplicationRunListener {

    void starting();

    void started(ConfigurableApplicationContext context);

    void environmentPrepared(ConfigurableEnvironment configurableEnvironment);

    void contextPrepared(ConfigurableApplicationContext configurableApplicationContext);

    void contextLoaded(ConfigurableApplicationContext context);

    void running(ConfigurableApplicationContext context);

    void failed(ConfigurableApplicationContext context, Throwable ex);

}
