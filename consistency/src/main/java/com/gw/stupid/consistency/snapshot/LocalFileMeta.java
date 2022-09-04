package com.gw.stupid.consistency.snapshot;

import lombok.Data;

import java.util.Properties;

/**
 * @author guanwu
 * @created 2022/9/4 11:30
 */

@Data
public class LocalFileMeta {

    private final Properties properties;

    public LocalFileMeta() {
        this.properties = new Properties();
    }

    public LocalFileMeta(Properties properties) {
        this.properties = properties;
    }

    public LocalFileMeta append(Object key, Object value) {
        this.properties.put(key, value);
        return this;
    }

    public Object get(String key) {
        return properties.getProperty(key);
    }

    @Override
    public String toString() {
        return "LocalFileMeta{" +
                "properties=" + properties +
                '}';
    }
}
