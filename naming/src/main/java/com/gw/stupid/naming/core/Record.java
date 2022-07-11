package com.gw.stupid.naming.core;

import java.io.Serializable;

/**
 * 数据
 *
 * @author guanwu
 * @created on 2022-07-11 15:58:27
 **/
public interface Record extends Serializable {

    /**
     * 获取校验和，通常用于record的比较
     * @return
     */
    String checkSum();

    /**
     * 重新计算校验和
     */
    void recalculateCheckSum();
}
