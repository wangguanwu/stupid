package com.gw.stupid.consistency.exception;

/**
 * @author guanwu
 * @created on 2022-09-05 14:33:59
 **/
public class ConsistencyException extends RuntimeException{

    public ConsistencyException() {
    }

    public ConsistencyException(String message) {
        super(message);
    }

    public ConsistencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsistencyException(Throwable cause) {
        super(cause);
    }

    public ConsistencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
