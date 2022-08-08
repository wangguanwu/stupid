package com.gw.stupid.naming.utils;


import com.gw.stupid.common.keygen.KeyGenerator;
import com.gw.stupid.common.keygen.ReflectKeyGenFactory;
import com.gw.stupid.common.keygen.SimpleDistributedGenerator;
import com.gw.stupid.env.EnvUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class NamingUtils {

    public static final String GROUP_DELIMITER = ":";

    public static final String LOCALHOST_SITE = "localhost-site";

    public static final String INSTANCE_GENERATE_KEY = "instance.generate.alg";

    public static String parseApp(String serviceName) {
        if (null == serviceName) {
            return null;
        }
        String[] split = serviceName.split(GROUP_DELIMITER);
        if (split.length > 1) {
            return split[1];
        }
        return split[0];
    }

    public static String parseGroup(String serviceName) {
        if (null == serviceName) {
            return null;
        }
        String[] split = serviceName.split(GROUP_DELIMITER);
        if (split.length > 1) {
            return split[0];
        }
        throw new IllegalArgumentException("Service name :" + serviceName + " is illegal");
    }

    /**
     * 生成instanceId
     *
     * @param existInstanceIdSet 已存在的instance id 集合，避免重复
     * @return next instance id
     */
    public static String generateInstanceId(Set<String> existInstanceIdSet) {
        KeyGenerator generator;
        try {
            generator = ReflectKeyGenFactory.
                    createKeyGenerator(EnvUtils.getInstanceIdGeneratorClassName());
        } catch (Exception e) {
            log.warn("Create key generator has error. err msg:{}, class name:{}, Using SimpleDistributedGenerator.",
                    e.getMessage(),
                    EnvUtils.getInstanceIdGeneratorClassName());
            try {
                generator = ReflectKeyGenFactory.createKeyGenerator(SimpleDistributedGenerator.class.getName());
            } catch (Exception ex) {
                throw new RuntimeException(String.format("Create key generator has error.err msg:%s, class name:%s",
                        ex.getMessage(), SimpleDistributedGenerator.class.getName()));
            }
        }

        Comparable<?> data = generator.generateKey();

        String id = String.valueOf(data);

        while (existInstanceIdSet.contains(id)) {
            id = String.valueOf(generator.generateKey());
        }
        return id;
    }
}
