package com.gw.stupid.api.common.keygen;

import com.gw.stupid.api.exception.StupidException;
import junit.framework.TestCase;

/**
 * @author guanwu
 * @created on 2022-08-08 11:30:39
 **/
public class ReflectKeyGenFactoryTest extends TestCase {

    public void testCreateKeyGenerator() throws StupidException {
        KeyGenerator keyGenerator = ReflectKeyGenFactory.createKeyGenerator("com.gw.stupid.api.common.keygen.SimpleDistributedGenerator");
        System.out.println(keyGenerator.getType());
    }

    public void testSnowflakeKeyGenerator() throws StupidException {
        KeyGenerator keyGenerator = ReflectKeyGenFactory.createKeyGenerator("com.gw.stupid.api.common.keygen.SnowflakeKeyGenerator");
        System.out.println(keyGenerator.generateKey());
    }

    public void testUUIDGenerator() throws StupidException {
        KeyGenerator keyGenerator = ReflectKeyGenFactory.createKeyGenerator("com.gw.stupid.api.common.keygen.UUIDShardingKeyGenerator");
        System.out.println(keyGenerator.generateKey());
    }
}