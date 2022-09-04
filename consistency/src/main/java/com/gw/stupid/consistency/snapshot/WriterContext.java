package com.gw.stupid.consistency.snapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guanwu
 * @created on 2022-08-10 16:34:57
 **/
public class WriterContext {

    private final Map<String, LocalFileMeta> fileMetaMap = new HashMap<>();

    private final String path;

    public WriterContext(String path) {
        this.path = path;
    }

    public void addFile(String fileName) {
        this.fileMetaMap.put(fileName, new LocalFileMeta().append("file-name",fileName));
    }

    public void addFile(String fileName, LocalFileMeta localFileMeta) {
        this.fileMetaMap.put(fileName, localFileMeta);
    }

    public Object removeFile(String fileName) {
        return this.fileMetaMap.remove(fileName);
    }

    public Map<String, LocalFileMeta> listFiles(){
        return new HashMap<>(this.fileMetaMap);
    }

}
