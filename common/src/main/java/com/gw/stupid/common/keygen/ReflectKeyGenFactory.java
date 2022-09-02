package com.gw.stupid.common.keygen;

import com.gw.stupid.api.enums.ApiErrorCodeEnum;
import com.gw.stupid.api.exception.StupidException;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guanwu
 * @created on 2022-08-08 11:16:55
 **/
public class ReflectKeyGenFactory<T extends KeyGenerator> {

    static Map<String, KeyGenerator> cacheMap = new ConcurrentHashMap<>(4);

    public static <T extends KeyGenerator> KeyGenerator createKeyGenerator(String className) throws StupidException {
        if (cacheMap.containsKey(className)) {
            return cacheMap.get(className);
        }
        try {
            Class<T> keygenClazz = (Class<T>) Class.forName(className);
            Constructor<T> constructor = (Constructor<T>) keygenClazz.getConstructors()[0];
            KeyGenerator keyGenerator =  constructor.newInstance();
            cacheMap.putIfAbsent(className, keyGenerator);
            return cacheMap.get(className);
        } catch (Throwable ex) {
            throw new StupidException(ApiErrorCodeEnum.INTERNAL_ERROR.code, "Create Key Generator has error." + ex.getMessage(), ex);
        }
    }
}
