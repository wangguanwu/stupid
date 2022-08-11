package com.gw.stupid.core.exception;


import com.gw.stupid.api.exception.StupidException;

/**
 * @author guanwu
 * @created on 2022-08-11 14:19:33
 **/
public class StorageException extends StupidException {

    private static final long serialVersionUID = -8785238328272635122L;

    public StorageException() {
    }

    public StorageException(String errMsg) {
        super(errMsg);
    }

    public StorageException(int errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public StorageException(int errCode, Throwable t) {
        super(errCode, t);
    }

    public StorageException(int errCode, String errMsg, Throwable t) {
        super(errCode, errMsg, t);
    }

    public StorageException(Throwable t) {
        super(t);
    }
}
