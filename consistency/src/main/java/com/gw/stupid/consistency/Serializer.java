package com.gw.stupid.consistency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guanwu
 * @created on 2022-08-09 16:00:13
 **/
public interface Serializer {
    Map<String, Class<?>> CLASS_NAME = new ConcurrentHashMap<>(8);

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] data);

    <T> T deserialize(byte[] data, Class<T> objClazz);

    default <T> T deserialize(byte[] data, String className) {
        T res;
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
            CLASS_NAME.putIfAbsent(className, clazz);
            Class cls = CLASS_NAME.get(className);
            return (T) deserialize(data, cls);
        } catch (Exception e) {
            return null;
        }
    }

    String name();
}
