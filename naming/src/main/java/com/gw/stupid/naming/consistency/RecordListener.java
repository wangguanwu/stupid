package com.gw.stupid.naming.consistency;

import com.gw.stupid.naming.core.Record;

/**
 * service信息监听器
 *
 * @author guanwu
 * @created on 2022-07-11 16:24:47
 **/
public interface RecordListener <T extends Record> {
    /**
     * 数据有变更
     * @param key
     * @param value
     */
    void onChange(final String key, final T value);

    /**
     * 数据删除
     * @param key
     */
    void onDelete(final String key);
}
