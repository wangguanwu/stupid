package com.gw.stupid.api.exception;

import java.io.Serializable;

/**
 * @author guanwu
 * @created on 2022-07-27 15:44:50
 **/
public class StupidException extends Exception implements Serializable {

    private static final long serialVersionUID = -8630001808484565092L;

    private int errCode;
    private String errMsg;
    private Throwable causeThrowable;

    public StupidException() {

    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setCauseThrowable(Throwable throwable) {
        this.causeThrowable = getCauseThrowable(throwable);
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Throwable getCauseThrowable() {
        return causeThrowable;
    }

    public StupidException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public StupidException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public StupidException(int errCode, Throwable t) {
        super(t);
        this.errCode = errCode;
        this.setCauseThrowable(t);
    }

    public StupidException(int errCode, String errMsg, Throwable t) {
        super(errMsg, t);
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.setCauseThrowable(t);
    }


    public StupidException(Throwable t) {
        super(t);
    }

    private Throwable getCauseThrowable(final Throwable t) {
        if (t.getCause() == null) {
            return t;
        }
        return this.getCauseThrowable(t.getCause());
    }

    @Override
    public String toString() {
        return "StupidException{" +
                "errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }

    public static final int INVALID_PARAM = 400;

    public static final int INTERNAL_ERROR = 500;
}
