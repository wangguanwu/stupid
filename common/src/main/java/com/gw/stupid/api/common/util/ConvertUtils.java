package com.gw.stupid.api.common.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;

/**
 * @author guanwu
 * @created on 2022-08-23 15:56:29
 **/
public class ConvertUtils {

    public final static String NULL_STR = "null";

    public static int toInt(String val) {
        return toInt(val, 0);
    }

    public static int toInt(String val, int defaultValue) {
        if(StringUtils.equals(val, NULL_STR)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean toBoolean(String val, boolean defaultValue) {
        if (StringUtils.isEmpty(val)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(val);
    }
}
