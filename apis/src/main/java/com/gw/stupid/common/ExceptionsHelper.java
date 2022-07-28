package com.gw.stupid.common;

import com.gw.stupid.enums.ApiErrorCodeEnum;
import com.gw.stupid.exception.runtime.StupidRuntimeException;

/**
 * @author guanwu
 * @created on 2022-07-28 16:11:08
 **/
public class ExceptionsHelper {

    public static StupidRuntimeException createRuntimeException(ApiErrorCodeEnum code) {
        return new StupidRuntimeException(code.code, code.errMsg);
    }
}
