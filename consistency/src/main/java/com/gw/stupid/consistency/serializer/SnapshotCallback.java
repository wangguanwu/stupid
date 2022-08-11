package com.gw.stupid.consistency.serializer;

/**
 * @author guanwu
 * @created on 2022-08-10 16:37:54
 **/
public interface SnapshotCallback {

    void callBack(boolean status, Throwable throwable);
}
