package com.gw.stupid.common.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyBuilderUtils {

    public final static String NAMESPACE_ID_DELIMITER = "##";

    public final static String SERVICE_EPHEMERAL_KEY = "ephemeral.";

    public final static String SERVICE_META_KEY_PREFIX = "com.gw.stupid.meta.key.";

    public final static String INSTANCE_LIST_KEY_PREFIX = "com.gw.stupid.ip.list.";


    public static String buildConsistencyInstanceListKey(String namespaceId, String serviceName, boolean isEphemeral) {
        if (isEphemeral) {
            return buildEphemeralInstanceListKey(namespaceId, serviceName);
        }
        return buildConsistencyInstanceListKey(namespaceId, serviceName);
    }

    public static String buildInstanceListKey(String namespaceId, String serviceName, boolean isEphemeral) {
        if (isEphemeral) {
            return buildEphemeralInstanceListKey(namespaceId, serviceName);
        }
        return buildConsistencyInstanceListKey(namespaceId, serviceName);
    }


    public static String buildEphemeralInstanceListKey(String namespaceId, String serviceName) {
        return INSTANCE_LIST_KEY_PREFIX +
                SERVICE_EPHEMERAL_KEY +
                namespaceId +
                NAMESPACE_ID_DELIMITER +
                serviceName;
    }

    public static String buildConsistencyInstanceListKey(String namespaceId, String serviceName) {
        return INSTANCE_LIST_KEY_PREFIX +
                namespaceId +
                NAMESPACE_ID_DELIMITER +
                serviceName;
    }
}
