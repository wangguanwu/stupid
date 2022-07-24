package com.gw.stupid.naming.utils;


public class NamingUtils {

    public static String GROUP_DELIMITER = ":";

    public static String parseApp(String serviceName) {
        if (null == serviceName){
            return null;
        }
        String[] split = serviceName.split(GROUP_DELIMITER);
        if (split.length > 1) {
            return split[1];
        }
        return split[0];
    }

    public static String parseGroup(String serviceName) {
        if (null == serviceName){
            return null;
        }
        String[] split = serviceName.split(GROUP_DELIMITER);
        if (split.length > 1) {
            return split[0];
        }
        throw new IllegalArgumentException("Service name :" +serviceName +" is illegal");
    }
}
