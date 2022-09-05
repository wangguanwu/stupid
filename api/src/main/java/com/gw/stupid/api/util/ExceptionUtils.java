package com.gw.stupid.api.util;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.PrintStream;

/**
 * @author guanwu
 * @created on 2022-09-05 14:17:22
 **/
public class ExceptionUtils {

    public static String getStackTrace(Throwable ex) {
        ByteOutputStream bos = new ByteOutputStream();
        PrintStream ps = new PrintStream(bos);
        ex.printStackTrace(ps);
        ps.flush();
        return bos.toString();
    }
}
