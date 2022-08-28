package com.gw.stupid.core.distributed.cp.raft.exception;

import java.io.Serializable;

public class JRaftDuplicateGroupException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -4478799493378054014L;

    public JRaftDuplicateGroupException() {
        super();
    }

    public JRaftDuplicateGroupException(String message) {
        super(message);
    }

    public JRaftDuplicateGroupException(String message, Throwable cause) {
        super(message, cause);
    }

    public JRaftDuplicateGroupException(Throwable cause) {
        super(cause);
    }

    protected JRaftDuplicateGroupException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
