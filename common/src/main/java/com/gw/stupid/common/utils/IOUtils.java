package com.gw.stupid.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * IO操作工具类
 *
 * @author guanwu
 * @created on 2022-08-04 18:53:03
 **/
public class IOUtils {

    public static BufferedReader convert2BufferedReader(Reader r) {
        return r instanceof BufferedReader
                ? (BufferedReader)r
                : new BufferedReader(r);
    }

    public static List<String> readLines(Reader r) throws IOException {
        BufferedReader br = convert2BufferedReader(r);
        List<String> res = new ArrayList<>();
        String tmp;
        while((tmp = br.readLine()) != null) {
            res.add(tmp);
        }
        return res;
    }
}
