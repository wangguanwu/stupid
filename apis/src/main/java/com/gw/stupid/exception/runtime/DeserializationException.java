package com.gw.stupid.exception.runtime;

import com.gw.stupid.enums.ApiErrorCodeEnum;

/**
 * @author guanwu
 * @created on 2022-08-10 10:52:31
 **/
public class DeserializationException extends StupidRuntimeException{

    private static final long serialVersionUID = 3104621994420327373L;

    public static final String DEFAULT_MSG = "Stupid deserialize failed";

    public static final String FORMAT_CLASS_MSG = "Stupid deserialize class: 【%s】failed";

    protected Class<?> clazz;

    protected Throwable cause;

    public DeserializationException() {
        super(ApiErrorCodeEnum.SERIALIZE_ERROR.code, DEFAULT_MSG);
    }

    public DeserializationException(int errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public DeserializationException(Class<?> clazz) {
        super(ApiErrorCodeEnum.DESERIALIZE_ERROR.code,
                String.format(FORMAT_CLASS_MSG, clazz.getName()));
        this.clazz = clazz;
    }

    public DeserializationException(Throwable cause) {
        super(ApiErrorCodeEnum.DESERIALIZE_ERROR.code,
                DEFAULT_MSG, cause);
        this.cause = cause;
    }

    public DeserializationException(Throwable cause, Class<?> clazz) {
        super(ApiErrorCodeEnum.DESERIALIZE_ERROR.code, String.format(FORMAT_CLASS_MSG, clazz.getName()),
                cause);
        this.clazz = clazz;
        this.cause = cause;
    }

    public DeserializationException(int code, String msg, Throwable cause) {
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
