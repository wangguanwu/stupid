package com.gw.stupid.common.keygen;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author guanwu
 * @created on 2022-08-08 11:11:08
 **/
public class SimpleDistributedGenerator implements KeyGenerator{

    private final AtomicLong counter = new AtomicLong(0);

    private Properties properties = new Properties();

    @Override
    public String getType() {
        return "SIMPLE";
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Comparable<?> generateKey() {
        return counter.incrementAndGet();
    }
}
