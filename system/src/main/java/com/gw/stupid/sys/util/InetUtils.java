package com.gw.stupid.sys.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guanwu
 * @created on 2022-07-29 10:41:52
 **/

@Slf4j
public class InetUtils {

    private static String currentPeerIp;

    private static boolean preferHostName = false;

    private static String selfIp;

    private static final List<String> PREFERRED_NETWORKS = new ArrayList<String>();

    private static final List<String> IGNORED_INTERFACES = new ArrayList<String>();

    static {
        //todo 刷新selfIp
    }



    public static String getCurrentPeerIp() {
        return currentPeerIp;
    }

    public static String getSelfIP() {
        return selfIp;
    }



}
