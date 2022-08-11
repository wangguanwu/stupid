package com.gw.stupid.consistency;

import com.gw.stupid.consistency.serializer.HessianSerializer;
import com.gw.stupid.consistency.snapshot.JsonSerializer;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guanwu
 * @created on 2022-08-10 14:35:44
 **/
public class SerializerFactory {

    public static final String HESSIAN_KEY = "Hessian".toLowerCase();

    public static final String JSON_KEY = "Json".toLowerCase();

    private static final Map<String, Serializer> SERIALIZER_MAP = new ConcurrentHashMap<>(8);

    public static  String defaultSerializer = HESSIAN_KEY;

    static {
        init(null);
    }

    public static void init(String defaultSerializer) {
        if (StringUtils.isNotEmpty(defaultSerializer)) {
            defaultSerializer = defaultSerializer;
        }
        SERIALIZER_MAP.computeIfAbsent(HESSIAN_KEY, (k) -> new HessianSerializer());
        SERIALIZER_MAP.computeIfAbsent(JSON_KEY, (k) -> new JsonSerializer());
    }

    public static void register(Serializer serializer) {
        SERIALIZER_MAP.put(serializer.name().toLowerCase(), serializer);
    }

    public static Serializer getDefault() {
        return SERIALIZER_MAP.get(defaultSerializer);
    }

    public static Serializer getSerializer(String name) {
        return SERIALIZER_MAP.get(name);
    }
}
