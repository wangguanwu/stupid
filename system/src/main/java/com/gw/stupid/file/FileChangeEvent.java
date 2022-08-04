package com.gw.stupid.file;

import lombok.ToString;

import java.io.Serializable;

/**
 * @author guanwu
 * @created on 2022-08-03 14:23:41
 **/

@ToString
public class FileChangeEvent implements Serializable {
    private static final long serialVersionUID = -2444063793332553082L;

    private Object context;

    private String paths;

    public FileChangeEvent(Object context, String paths) {
        this.context = context;
        this.paths = paths;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }
}
