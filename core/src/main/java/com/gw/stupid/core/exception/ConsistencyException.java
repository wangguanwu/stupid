package com.gw.stupid.core.exception;

/**
 * @author guanwu
 * @created 2022/9/4 11:53
 */
public class ConsistencyException extends RuntimeException{
    private static final long serialVersionUID = 2412963547505474942L;

    public ConsistencyException() {
        super();
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

    protected ConsistencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
