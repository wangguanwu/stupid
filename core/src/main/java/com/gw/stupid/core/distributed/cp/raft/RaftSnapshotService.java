package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.entity.LocalFileMetaOutter;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.google.protobuf.ZeroByteStringHelper;
import com.gw.stupid.common.util.JacksonUtils;
import com.gw.stupid.consistency.snapshot.LocalFileMeta;

/**
 * @author guanwu
 * @created on 2022-09-02 16:22:12
 **/
public interface RaftSnapshotService {

    void saveSnapshot(SnapshotWriter snapshotWriter, Closure closure);

    void loadSnapshot(SnapshotReader snapshotReader);

    default LocalFileMetaOutter.LocalFileMeta buildMetadata(LocalFileMeta metadata) throws Exception{
        return metadata == null ? null : LocalFileMetaOutter.LocalFileMeta.newBuilder()
                .setUserMeta(ZeroByteStringHelper.wrap(JacksonUtils.toBytesArray(metadata))).
                build();
    }

}
