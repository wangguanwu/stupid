package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.entity.LocalFileMetaOutter;
import com.alipay.sofa.jraft.error.RaftError;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.gw.stupid.common.util.JacksonUtils;
import com.gw.stupid.consistency.snapshot.SnapshotCallback;
import com.gw.stupid.consistency.snapshot.LocalFileMeta;
import com.gw.stupid.consistency.snapshot.ReaderContext;
import com.gw.stupid.consistency.snapshot.SnapshotOperation;
import com.gw.stupid.consistency.snapshot.WriterContext;
import com.gw.stupid.core.exception.ConsistencyException;

import java.util.*;

public class RaftSnapShotAdapter implements RaftSnapshotService{

    private final SnapshotOperation snapshotOperation;

    public RaftSnapShotAdapter(SnapshotOperation snapshotOperation) {
        this.snapshotOperation = snapshotOperation;
    }

    @Override
    public void saveSnapshot(SnapshotWriter snapshotWriter, Closure closure) {
        WriterContext writerContext = new WriterContext(snapshotWriter.getPath());
        final SnapshotCallback callback= (result, t) -> {
            List<Boolean>res = new ArrayList<>(writerContext.listFiles().size());
            int []index = new int[]{0};
            writerContext.listFiles().forEach((file,meta) -> {
                try {
                    res.set(index[0]++,snapshotWriter.addFile(file, buildMetadata(meta)));
                } catch (Exception e) {
                    throw new ConsistencyException(e);
                }
                boolean isOk = result &&  res.stream().noneMatch(Boolean.FALSE::equals);
                final Status status = isOk ? Status.OK() : new Status(RaftError.EIO, "Fail to compress" +
                        "snapshot: %s, error: %s", file, t.getMessage());
                closure.run(status);
            });
        };
        this.snapshotOperation.saveSnapshot(writerContext, callback);
    }

    @Override
    public void loadSnapshot(SnapshotReader snapshotReader) {

        Map<String, LocalFileMeta> metaMap = new HashMap<>(snapshotReader.listFiles().size());

        for (String fileName : snapshotReader.listFiles()) {
            final LocalFileMetaOutter.LocalFileMeta localFileMeta = (LocalFileMetaOutter.LocalFileMeta)
                    snapshotReader.getFileMeta(fileName);

            byte[] bytes = localFileMeta.getUserMeta().toByteArray();

            final LocalFileMeta meta;
            if (null == bytes || bytes.length == 0) {
                meta = new LocalFileMeta();
            } else {
                meta = JacksonUtils.parseJson(bytes, LocalFileMeta.class);
            }

            metaMap.put(fileName, meta);
        }

        ReaderContext readerContext = new ReaderContext(snapshotReader.getPath(),
                metaMap);

        this.snapshotOperation.loadSnapshot(readerContext);

    }
}
