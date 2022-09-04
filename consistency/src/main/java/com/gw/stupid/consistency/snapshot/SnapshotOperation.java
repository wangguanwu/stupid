package com.gw.stupid.consistency.snapshot;

/**
 * @author guanwu
 * @created on 2022-08-10 16:33:42
 **/
public interface SnapshotOperation {

    void saveSnapshot(WriterContext writerContext, SnapshotCallback callback);

    boolean loadSnapshot(ReaderContext readerContext);

}
