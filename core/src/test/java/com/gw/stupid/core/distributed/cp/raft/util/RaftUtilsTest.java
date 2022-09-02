package com.gw.stupid.core.distributed.cp.raft.util;

import com.alipay.sofa.jraft.option.NodeOptions;
import junit.framework.TestCase;

/**
 * @author guanwu
 * @created on 2022-09-02 15:39:53
 **/
public class RaftUtilsTest extends TestCase {


    public void testInitDirectory() {

        String test = "/Users/guanwu/tmp/test/";
        String group = "test-group";

        RaftUtils.initDirectory(test, group, new NodeOptions());
    }
}