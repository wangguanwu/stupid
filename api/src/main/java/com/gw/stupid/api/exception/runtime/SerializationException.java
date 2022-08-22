package com.gw.stupid.api.exception.runtime;

import com.gw.stupid.api.enums.ApiErrorCodeEnum;

/**
 * @author guanwu
 * @created on 2022-08-09 17:04:23
 **/
public class SerializationException extends StupidRuntimeException{

    private static final long serialVersionUID = -8265437992134194502L;

    public static final String DEFAULT_MSG = "Stupid serialize failed";

    public static final String FORMAT_CLASS_MSG = "Stupid serialize class: 【%s】failed";

    protected Class<?> clazz;

    protected Throwable cause;

    public SerializationException() {
        super(ApiErrorCodeEnum.SERIALIZE_ERROR.code, DEFAULT_MSG);
    }


    public SerializationException(int errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public SerializationException(Class<?> clazz) {
        super(ApiErrorCodeEnum.SERIALIZE_ERROR.code,
                String.format(FORMAT_CLASS_MSG, clazz.getName()));
        this.clazz = clazz;
    }

    public SerializationException(Throwable cause) {
        super(ApiErrorCodeEnum.SERIALIZE_ERROR.code,
                DEFAULT_MSG, cause);
        this.cause = cause;
    }


    public SerializationException(Throwable cause, Class<?> clazz) {
        super(ApiErrorCodeEnum.SERIALIZE_ERROR.code, String.format(FORMAT_CLASS_MSG, clazz.getName()),
                cause);
        this.clazz = clazz;
        this.cause = cause;
    }

    public SerializationException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
        this.cause = cause;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Throwable getCause() {
        return cause;
    }
}
