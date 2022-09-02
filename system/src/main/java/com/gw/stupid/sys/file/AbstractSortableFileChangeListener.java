package com.gw.stupid.sys.file;


/**
 * 可排序的listener
 *
 * @author guanwu
 * @created on 2022-08-03 14:42:45
 **/
public abstract class AbstractSortableFileChangeListener implements FileChangeListener{

    private final int order;

    public AbstractSortableFileChangeListener() {
       order = (int)(System.currentTimeMillis() / 1000);
    }

    @Override
    public int getSortValue() {
        return order;
    }

    @Override
    public int compareTo(FileChangeListener o) {
        return this.order - o.getSortValue();
    }
}
