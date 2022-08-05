package com.gw.stupid.naming.utils;


import java.util.Set;

public class NamingUtils {

    public static final String GROUP_DELIMITER = ":";

    public static final String LOCALHOST_SITE = "localhost-site";

    public static final String INSTANCE_GENERATE_KEY = "instance.generate.alg";

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

    public static String generateInstanceId(Set<String> existInstanceIdSet) {

    }
}
