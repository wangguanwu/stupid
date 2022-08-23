package com.gw.stupid.consistency.serializer;

import com.gw.stupid.api.common.util.JacksonUtils;
import com.gw.stupid.consistency.Serializer;

/**
 * @author guanwu
 * @created on 2022-08-09 16:23:08
 **/
public class JsonSerializer implements Serializer {

    private final static String NAME = "Json";

    @Override
    public <T> byte[] serialize(T obj) {
        return JacksonUtils.toBytesArray(obj);
    }

    @Override
    public <T> T deserialize(byte[] data) {
        throw new UnsupportedOperationException("JsonSerializer does not support Deserialization without type");

    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> objClazz) {
        return JacksonUtils.parseJson(data, objClazz);
    }

    @Override
    public String name() {
        return NAME;
    }
}
