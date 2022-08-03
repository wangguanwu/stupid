package com.gw.stupid.file;

import java.util.concurrent.Executor;

/**
 * @author guanwu
 * @created on 2022-08-03 14:24:23
 **/
public interface FileChangeListener extends Comparable<FileChangeListener> {

    void onChange(FileChangeEvent event);

    boolean interest(String path);

    int getSortValue();

    /**
     * 如果拥有自己的执行器，需要
     * @return
     */
    default Executor getExecutor() {
        return null;
    }
}
