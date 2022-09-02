package com.gw.stupid.core.distributed.cp.raft.util;

import com.alipay.sofa.jraft.option.RaftOptions;
import com.gw.stupid.common.util.ConvertUtils;
import com.gw.stupid.core.distributed.cp.raft.RaftConfig;

/**
 * @author guanwu
 * @created on 2022-08-23 16:09:23
 **/
public class RaftOptionsBuildUtils {

    /**
     * 创建一些自定义参数
     * @param raftConfig
     * @return
     */
    public static RaftOptions initRaftOptions(RaftConfig raftConfig) {
        RaftOptions raftOptions = new RaftOptions();

        raftOptions.setMaxByteCountPerRpc(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.RAFT_MAX_BYTE_COUNT_PER_RPC),
                RaftConstants.DEFAULT_MAX_BYTE_COUNT_PER_RPC));

        raftOptions.setMaxEntriesSize(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.MAX_ENTRIES_SIZE),
                        RaftConstants.DEFAULT_MAX_ENTRIES_SIZE));

        raftOptions.setMaxBodySize(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.MAX_BODY_SIZE),
                        RaftConstants.DEFAULT_MAX_BODY_SIZE));

        raftOptions.setMaxAppendBufferSize(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.MAX_APPEND_BUFFER_SIZE),
                        RaftConstants.DEFAULT_MAX_APPEND_BUFFER_SIZE));

        raftOptions.setMaxElectionDelayMs(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.MAX_ELECTION_DELAY_MS),
                        RaftConstants.DEFAULT_MAX_ELECTION_DELAY_MS));

        raftOptions.setElectionHeartbeatFactor(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.ELECTION_HEARTBEAT_FACTOR),
                        RaftConstants.DEFAULT_ELECTION_HEARTBEAT_FACTOR));

        raftOptions.setApplyBatch(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.APPLY_BATCH),
                        RaftConstants.DEFAULT_APPLY_BATCH));

        raftOptions.setSync(ConvertUtils.toBoolean(raftConfig.getValue(RaftConstants.SYNC),
                        RaftConstants.DEFAULT_SYNC));

        raftOptions.setSyncMeta(ConvertUtils.toBoolean(raftConfig.getValue(RaftConstants.SYNC_META),
                RaftConstants.DEFAULT_SYNC_META));

        raftOptions.setDisruptorBufferSize(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.DISRUPTOR_BUFFER_SIZE),
                RaftConstants.DEFAULT_DISRUPTOR_BUFFER_SIZE));

        raftOptions.setMaxReplicatorInflightMsgs(ConvertUtils.toInt(raftConfig.getValue(RaftConstants.MAX_REPLICATOR_INFLIGHT_MSGS),
                RaftConstants.DEFAULT_MAX_REPLICATOR_INFLIGHT_MSGS));

        raftOptions.setEnableLogEntryChecksum(ConvertUtils.toBoolean(raftConfig.getValue(RaftConstants.ENABLE_LOG_ENTRY_CHECKSUM),
                RaftConstants.DEFAULT_ENABLE_LOG_ENTRY_CHECKSUM));

        return raftOptions;
    }
}
