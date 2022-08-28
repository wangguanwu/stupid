package com.gw.stupid.core.distributed.cp.raft.exception;

import java.io.Serializable;

public class JRaftRuntimeException extends RuntimeException implements Serializable {


    private static final long serialVersionUID = 1412942266783377112L;

    public JRaftRuntimeException() {
    }

    public JRaftRuntimeException(String message) {
        super(message);
    }

    public JRaftRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRaftRuntimeException(Throwable cause) {
        super(cause);
    }

    public JRaftRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
