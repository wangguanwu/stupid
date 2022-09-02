package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;

/**
 * @author guanwu
 * @created on 2022-09-02 16:22:12
 **/
public interface RaftSnapshotService {

    void saveSnapshot(SnapshotWriter snapshotWriter, Closure closure);

    void loadSnapshot(SnapshotReader snapshotReader);

}
