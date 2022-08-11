package com.gw.stupid.api.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author guanwu
 * @created on 2022-07-29 10:41:52
 **/

@Slf4j
public class InetUtils {

    private static String currentPeerIp;

    private static boolean preferHostName = false;



    public static String getCurrentPeerIp() {
        return currentPeerIp;
    }


}
