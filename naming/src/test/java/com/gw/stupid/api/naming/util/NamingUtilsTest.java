package com.gw.stupid.api.naming.util;

import com.gw.stupid.sys.env.EnvUtils;
import junit.framework.TestCase;
import org.springframework.mock.env.MockEnvironment;

import java.util.HashSet;

/**
 * @author guanwu
 * @created on 2022-08-08 14:48:43
 **/
public class NamingUtilsTest extends TestCase {

    @Override
    protected void setUp() throws Exception {

        MockEnvironment configurableEnvironment  = new MockEnvironment();
//        configurableEnvironment.setProperty("instance.id.generator.conf.class.name",
//                "com.gw.stupid.api.common.keygen.SnowflakeKeyGenerator");
        EnvUtils.setEnvironment(configurableEnvironment);

    }

    public void testGenerateInstanceId() {
        int i = 0;
        while(i++ < 10) {
            String instanceId = NamingUtils.generateInstanceId(new HashSet<>());
            System.out.println(instanceId);
        }
    }
}