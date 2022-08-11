package com.gw.stupid.api.exception.runtime;

import java.io.Serializable;

/**
 * @author guanwu
 * @created on 2022-07-27 16:06:23
 **/
public class StupidRuntimeException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -1389510264871167996L;

    private int errCode;
    private String errMsg;
    private Throwable causeThrowable;

    public StupidRuntimeException(int errCode, Throwable throwable) {
        super(throwable);
        this.errCode = errCode;
        setCauseThrowable(throwable);
    }

    public StupidRuntimeException(int errCode, String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public StupidRuntimeException(int errCode, String errMsg, Throwable t) {
        super(errMsg, t);
        this.errCode = errCode;
        this.errMsg = errMsg;
        setCauseThrowable(t);
    }

    public void setCauseThrowable(Throwable t) {
        this.causeThrowable = getCauseThrowable(t);
    }

    private Throwable getCauseThrowable(Throwable t) {
        if (t.getCause() == null) {
            return t;
        }
        return getCauseThrowable(t.getCause());
    }

    public Throwable getCauseThrowable() {
        return causeThrowable;
    }

    @Override
    public String toString() {
        return "StupidRuntimeException{" +
                "errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
