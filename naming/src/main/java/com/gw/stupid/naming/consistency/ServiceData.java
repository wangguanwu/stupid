package com.gw.stupid.naming.consistency;

import com.gw.stupid.naming.core.Record;

import java.io.Serializable;

/**
 * Naming数据
 *
 * @author guanwu
 * @created on 2022-07-11 16:06:49
 *
 **/
public class ServiceData <T extends Record>  implements Serializable {
    private static final long serialVersionUID = 7739839005107359707L;
    public String key;
    public T value;

    public ServiceData(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public static <T extends Record> ServiceData<T> createServiceData(String key, T value) {
        return new ServiceData<T>(key, value);
    }
}
