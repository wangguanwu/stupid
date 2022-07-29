package com.gw.stupid.env;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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