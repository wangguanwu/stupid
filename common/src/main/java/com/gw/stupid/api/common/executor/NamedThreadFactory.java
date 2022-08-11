package com.gw.stupid.api.common.executor;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 *
 * @author guanwu
 * @created on 2022-08-04 11:05:57
 **/
public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private final String namePrefix;

    private final static String DEFAULT_NAME_PREFIX = "stupid-thread";

    private final static String NAME_CONNECTOR = "-";

    public NamedThreadFactory() {
        this.namePrefix = DEFAULT_NAME_PREFIX;
    }

    public NamedThreadFactory(String namePrefix) {
        if (StringUtils.isEmpty(namePrefix)) {
            this.namePrefix = DEFAULT_NAME_PREFIX;
        } else {
            this.namePrefix = namePrefix;
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(namePrefix + NAME_CONNECTOR + counter.incrementAndGet());
        t.setDaemon(true);
        return t;
    }
}
