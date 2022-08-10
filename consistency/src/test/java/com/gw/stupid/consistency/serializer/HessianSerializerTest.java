package com.gw.stupid.consistency.serializer;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guanwu
 * @created on 2022-08-09 16:42:29
 **/
public class HessianSerializerTest extends TestCase {

    Map<String, String> testMap = new HashMap<>();
    HessianSerializer serializer;

    public void setUp() throws Exception {
        super.setUp();
        testMap.put("hello", "world");
        testMap.put("one", "two");
        serializer = new HessianSerializer();
    }

    public void testSerialize() {
        byte[] serialize = serializer.serialize(testMap);
        System.out.println(new String(serialize));
    }

    public void testDeserialize() {
        byte[] serialize = serializer.serialize(testMap);
        System.out.println(serializer.deserialize(serialize, Map.class));
    }

    public void testTestDeserialize() {
    }

    public void testName() {
    }
}