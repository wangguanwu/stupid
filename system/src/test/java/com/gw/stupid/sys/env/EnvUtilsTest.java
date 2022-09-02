package com.gw.stupid.sys.env;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author guanwu
 * @created on 2022-07-29 16:44:53
 **/
class EnvUtilsTest {

    @Test
    void getRelativeResource() throws IOException {
        Resource relativeResource = EnvUtils.getRelativeResource("","");
    }
}