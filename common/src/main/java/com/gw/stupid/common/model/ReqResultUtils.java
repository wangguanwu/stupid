package com.gw.stupid.common.model;

/**
 * @author guanwu
 * @created on 2022-08-08 16:50:37
 **/
public class ReqResultUtils {

    public static<T> ReqResult<T> success() {
        return ReqResult.<T>builder()
                .code(200)
                .build();
    }
}
