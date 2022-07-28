package com.gw.stupid.naming.consistency;

import com.gw.stupid.exception.StupidException;
import com.gw.stupid.naming.core.Record;

/**
 * @author guanwu
 * @created on 2022-07-27 17:06:00
 *
 * 分布式一致性服务服务
 **/
public interface CpService {

    /**
     * 将key,value put进Stupid Cluster
     * @param key
     * @param value
     */
    void put(String key, Record value) throws StupidException;

    void remove(String key) throws StupidException;

    Datum<? extends Record> get(String key) throws StupidException;


}
