package com.gw.stupid.util;

/**
 * @author guanwu
 * @created on 2022-08-04 16:26:40
 **/
public class ThreadUtils {

    public static void addShutdownHook(Runnable r) {
        Runtime.getRuntime().addShutdownHook(new Thread(r));
    }
}
