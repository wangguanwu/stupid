package com.gw.stupid.core.store;

import com.gw.stupid.core.exception.StorageException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author guanwu
 * @created on 2022-08-11 14:13:45
 *
 * 通用keyValue存储接口
 **/
public interface KvStorage {

    enum StoreType {
        File,
        Memory
    }

    byte[] get(String key) throws StorageException;

    Map<String , byte[]> batchGet(List<String> keys) throws StorageException;

    void put(String key, byte[] value) throws StorageException;

    void batchPut(List<String> keys, List<byte[]> values) throws StorageException;

    void delete(String key) throws StorageException;

    void batchDelete(List<String> keys) throws StorageException;

    void makeSnapshot(String path) throws StorageException;

    void loadSnapshot(String path) throws StorageException;

    Set<String> keySet() throws StorageException;

    void init() throws StorageException;

    void shutdown() throws StorageException;

}
