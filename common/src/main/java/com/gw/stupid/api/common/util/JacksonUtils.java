package com.gw.stupid.api.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gw.stupid.api.exception.runtime.DeserializationException;
import com.gw.stupid.api.exception.runtime.SerializationException;

import java.io.IOException;

/**
 * @author guanwu
 * @created on 2022-08-09 16:56:34
 **/
public class JacksonUtils {

    static class ObjectMapperHolder {
        private static final ObjectMapper MAPPER = new ObjectMapper();
        static {
            MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    }

    public static ObjectMapper getInstance() {
        return ObjectMapperHolder.MAPPER;
    }

    public static<T> String toJsonString(T obj) {
        try {
            return getInstance().writeValueAsString(obj);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    public static<T> T parseJson(String json, Class<T> clazz) {
        try {
            return getInstance().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new DeserializationException(e);
        }
    }

    public static<T> T parseJson(String json, TypeReference<T> tTypeReference) {
        try {
            return getInstance().readValue(json, tTypeReference);
        } catch (JsonProcessingException e) {
            throw new DeserializationException(e);
        }
    }

    public static<T> T parseJson(byte[] json, Class<T> clazz) {
        try {
            return getInstance().readValue(json, clazz);
        } catch (IOException e) {
            throw new DeserializationException(e);
        }
    }

    public static<T> byte[] toBytesArray(T obj) {
        try {
            return getInstance().writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }
}
