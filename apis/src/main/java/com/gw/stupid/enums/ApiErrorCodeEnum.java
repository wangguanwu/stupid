package com.gw.stupid.enums;

/**
 * @author guanwu
 * @created on 2022-07-28 15:53:32
 **/
public enum ApiErrorCodeEnum {
    INTERNAL_ERROR(500, "internal error"),

    SUCCESS(200, "ok"),

    ARGS_INVALID(400, "argument invalid");

    public final int code;

    public final String errMsg;

    private ApiErrorCodeEnum(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }
}
