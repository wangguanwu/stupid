package com.gw.stupid.core.listener.event;

import com.gw.stupid.core.listener.StupidApplicationRunListener;
import com.gw.stupid.core.listener.impl.StartingStupidAppRunListener;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guanwu
 * @created on 2022-08-01 19:44:30
 **/
public class StupidApplicationBootListener implements SpringApplicationRunListener, Ordered {
    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    private final List<StupidApplicationRunListener> listeners;

    public StupidApplicationBootListener() {
        initListener(this.listeners = new ArrayList<>());
    }

    private static void initListener(List<StupidApplicationRunListener> listeners) {
        listeners.add(new StartingStupidAppRunListener());
    }

    @Override
    public void starting() {
        for (StupidApplicationRunListener listener : listeners) {
            listener.starting();
        }
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        for (StupidApplicationRunListener listener : listeners) {
            listener.environmentPrepared(environment);
        }
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        for (StupidApplicationRunListener listener : listeners) {
            listener.contextPrepared(context);
        }
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        for (StupidApplicationRunListener listener : listeners) {
            listener.contextLoaded(context);
        }
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        for (StupidApplicationRunListener listener : listeners) {
            listener.started(context);
        }
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        for (StupidApplicationRunListener listener : listeners) {
            listener.running(context);
        }
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        for (StupidApplicationRunListener listener : listeners) {
            listener.failed(context, exception);
        }
    }
}
