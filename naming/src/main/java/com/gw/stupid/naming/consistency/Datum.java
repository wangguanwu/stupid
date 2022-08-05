package com.gw.stupid.naming.consistency;

import com.gw.stupid.naming.core.Record;

import java.io.Serializable;

/**
 * Stupid 数据
 *
 * @author guanwu
 * @created on 2022-07-27 17:21:52
 **/
public class Datum<T extends Record> implements Serializable {

    private static final long serialVersionUID = -3595734294429192543L;

    public String key;
    public T value;

    public Datum() {
    }

    public Datum(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public static<T extends Record> Datum<T> createInstance(String key, T value) {
        return new Datum<T>(key, value);
    }

}
