package com.gw.stupid.consistency.snapshot;

import com.gw.stupid.consistency.serializer.SnapshotCallback;

/**
 * @author guanwu
 * @created on 2022-08-10 16:33:42
 **/
public interface SnapshotOperation {

    void saveSnapshot(Writer writer, SnapshotCallback callback);

    boolean loadSnapshot(Reader reader);

}
