package com.gw.stupid.api.common.keygen;

import java.util.Properties;

/**
 * @author guanwu
 * @created on 2022-08-05 17:03:41
 **/
public interface KeyGenerator {
    /**
     * Get algorithm type.
     *
     * @return type
     */
    String getType();

    /**
     * Get properties.
     *
     * @return properties of algorithm
     */
    Properties getProperties();

    /**
     * Set properties.
     *
     * @param properties properties of algorithm
     */
    void setProperties(Properties properties);

    /**
     * Generate key.
     *
     * @return generated key
     */
    Comparable<?> generateKey();
}
