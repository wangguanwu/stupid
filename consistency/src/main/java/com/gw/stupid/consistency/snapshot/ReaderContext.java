package com.gw.stupid.consistency.snapshot;

import lombok.Data;

import java.util.Map;

/**
 * @author guanwu
 * @created 2022/9/4 12:15
 */

@Data
public class ReaderContext {

    private  String path;

    private Map<String, LocalFileMeta> fileMetaMap;

    public ReaderContext() {

    }

    public ReaderContext(String path, Map<String, LocalFileMeta> fileMetaMap) {
        this.path = path;
        this.fileMetaMap = fileMetaMap;
    }
}
