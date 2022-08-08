package com.gw.stupid.common.model;

import com.gw.stupid.common.constants.CommonConstants;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author guanwu
 * @created on 2022-08-08 16:50:24
 **/

@ToString
@Data
public class ReqResult<T> implements Serializable {
    private static final long serialVersionUID = 3861133489249511099L;

    private int code;

    private String msg;

    private T data;

    public ReqResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ReqResult() {
    }

    public ReqResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isOk() {
        return this.code == CommonConstants.REQ_SUCCESS;
    }

    public boolean isOk(int successCode) {
        return successCode == this.code;
    }

    public static<T> ReqResultBuilder<T> builder() {
        return new ReqResultBuilder<>();
    }

    public static class ReqResultBuilder<T> {
        int code;
        String msg;
        T data;

        private ReqResultBuilder() {}

        public ReqResultBuilder<T> code(int code) {
            this.code = code;
            return this;
        }


        public ReqResultBuilder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }


        public ReqResultBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ReqResult<T> build() {
            return new ReqResult<T>(code, msg, data);
        }
    }
}
