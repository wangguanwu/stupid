package com.gw.stupid.common.util;

/**
 * @author guanwu
 * @created on 2022-09-02 14:44:31
 **/
public class PropertyUtils {

    private static final String PROCESSORS_ENV_NAME = "NACOS_COMMON_PROCESSORS";

    private static final String PROCESSORS_PROP_NAME = "nacos.common.processors";

    public static String getProperty(String propertyName, String envName) {
        return System.getenv().getOrDefault(envName, System.getProperty(propertyName));
    }

    public static String getProperty(String propertyName, String envName, String defaultValue) {
        return System.getenv().getOrDefault(envName, System.getProperty(propertyName, defaultValue));
    }

    public static int getProcessorsCount() {
        int processorsCount = 0;

        String processorsCountPreSet = getProperty(PROCESSORS_ENV_NAME, PROCESSORS_ENV_NAME);
        if (processorsCountPreSet != null) {
            try {
                processorsCount = Integer.parseInt(processorsCountPreSet);
            } catch (NumberFormatException ex) {
                //ignore
            }
        }

        if (processorsCount <= 0) {
            processorsCount = Runtime.getRuntime().availableProcessors();
        }

        return processorsCount;
    }
}
