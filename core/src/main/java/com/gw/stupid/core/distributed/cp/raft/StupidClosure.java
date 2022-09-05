package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.error.RaftError;
import com.google.protobuf.Message;
import com.gw.stupid.api.manage.Clearable;
import com.gw.stupid.consistency.entity.auto.Response;

/**
 * Stupid 闭包实现
 * @author guanwu
 * @created on 2022-09-02 16:33:02
 **/
public class StupidClosure implements Closure, Clearable {

    private Message message;

    private Closure closure;

    private StupidStatus stupidStatus = new StupidStatus();

    public StupidClosure(Message message, Closure closure) {
        this.message = message;
        this.closure = closure;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public void run(Status status) {
        stupidStatus.setStatus(status);
        closure.run(status);
    }

    @Override
    public void clear() {
        this.message = null;
        this.closure = null;
        this.stupidStatus = null;
    }

    public void setResponse(Response response) {
        stupidStatus.setResponse(response);
    }

    public void setThrowable(Throwable throwable) {
        stupidStatus.setThrowable(throwable);
    }


    public void setMessage(Message message) {
        this.message = message;
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }

    public static class StupidStatus extends Status {

        private Throwable throwable;

        private Response response;

        private Status status;

        public void setStatus(Status status) {
            this.status = status;
        }

        public StupidStatus(Status status) {
            this.status = status;
        }

        public StupidStatus() {

        }

        public Throwable getThrowable() {
            return throwable;
        }

        public void setThrowable(Throwable throwable) {
            this.throwable = throwable;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public Status getStatus() {
            return status;
        }

        @Override
        public boolean isOk() {
            return status.isOk();
        }

        @Override
        public void setCode(int code) {
            status.setCode(code);
        }

        @Override
        public int getCode() {
            return status.getCode();
        }

        @Override
        public RaftError getRaftError() {
            return status.getRaftError();
        }

        @Override
        public void setErrorMsg(String errMsg) {
            status.setErrorMsg(errMsg);
        }

        @Override
        public void setError(int code, String fmt, Object... args) {
            status.setError(code, fmt, args);
        }

        @Override
        public void setError(RaftError error, String fmt, Object... args) {
            status.setError(error, fmt, args);
        }

        @Override
        public String toString() {
            return status.toString();
        }

        @Override
        public Status copy() {
            StupidStatus copy = new StupidStatus(status);
            copy.status = this.status;
            copy.response = this.response;
            copy.throwable = this.throwable;
            return copy;
        }

        @Override
        public String getErrorMsg() {
            return status.getErrorMsg();
        }
    }
}
