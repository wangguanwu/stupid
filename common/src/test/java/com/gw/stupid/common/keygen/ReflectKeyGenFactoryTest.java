package com.gw.stupid.common.keygen;

import com.gw.stupid.exception.StupidException;
import junit.framework.TestCase;

/**
 * @author guanwu
 * @created on 2022-08-08 11:30:39
 **/
public class ReflectKeyGenFactoryTest extends TestCase {

    public void testCreateKeyGenerator() throws StupidException {
        KeyGenerator keyGenerator = ReflectKeyGenFactory.createKeyGenerator("com.gw.stupid.common.keygen.SimpleDistributedGenerator");
        System.out.println(keyGenerator.getType());
    }
}