package com.gw.stupid.core.distributed.cp.raft.util;

/**
 * @author guanwu
 * @created on 2022-08-22 19:38:22
 **/
public interface RaftConstants {

    String RAFT_CORE_THREAD_NUM = "core_thread_num";

    String RAFT_CLI_SERVICE_THREAD_NUM = "cli_service_thread_num";

    String RAFT_ELECTION_TIMEOUT_MS = "election_timeout_ms";

    int DEFAULT_RAFT_ELECTION_TIMEOUT_MS = 5000;

    String RAFT_RPC_REQUEST_TIMEOUT_MS = "rpc_request_timeout_ms";

    int DEFAULT_RAFT_RPC_REQUEST_TIMEOUT_MS = 5000;

    String RAFT_MAX_BYTE_COUNT_PER_RPC = "max_byte_count_per_rpc";

    /**
     * 节点之间每次rpc请求的大小,默认为128k
     *
     */
    int DEFAULT_MAX_BYTE_COUNT_PER_RPC = 128 * 1024;

    String MAX_ENTRIES_SIZE = "max_entries_size";

    int DEFAULT_MAX_ENTRIES_SIZE = 1024;

    String MAX_BODY_SIZE = "max_body_size";

    /**
     * leader发送给follower的日志最大body大小, 默认为512K
     *
     */
    int DEFAULT_MAX_BODY_SIZE = 512 * 1024;

    /**
     * Maximum log storage buffer size, default 256K
     *
     */
    String MAX_APPEND_BUFFER_SIZE = "max_append_buffer_size";

    int DEFAULT_MAX_APPEND_BUFFER_SIZE = 256 * 1024;

    /**
     * Election timer interval will be a
     * random maximum outside the specified time, default is 1 second
     */
    String MAX_ELECTION_DELAY_MS = "max_election_delay_ms";

    int DEFAULT_MAX_ELECTION_DELAY_MS = 1000;

    String ELECTION_HEARTBEAT_FACTOR = "election_heartbeat_factor";

    /**
     * 指定选举超时时间和心跳间隔时间之间的比值。
     * 心跳间隔等于electionTimeoutMs/electionHeartbeatFactor，默认10分之一
     */
    int DEFAULT_ELECTION_HEARTBEAT_FACTOR = 10;

    /**
     * The tasks submitted to the leader accumulate the maximum batch size of a batch flush log storage. The default is
     *     32 tasks.
     */
    String APPLY_BATCH = "apply_batch";

    /**
     * 向 leader 提交的任务累积一个批次刷入日志存储的最大批次大小，默认 32 个任务
     */
    int DEFAULT_APPLY_BATCH = 32;

    /**
     * Call fsync when necessary when writing logs and meta information, usually should be true
     */
    String SYNC = "sync";

    boolean DEFAULT_SYNC = true;

    /**
     * Whether to write snapshot / raft meta-information to call fsync. The default is false. When sync is true, it is
     *      preferred to respect sync
     */
    String SYNC_META = "sync_meta";

    boolean DEFAULT_SYNC_META = false;

    /**
     * Internal disruptor buffer size, need to be appropriately adjusted for high write throughput applications, default
     *     16384 内部 disruptor buffer 大小，如果是写入吞吐量较高的应用，需要适当调高该值，默认 16384
     */
    String DISRUPTOR_BUFFER_SIZE = "disruptor_buffer_size";

    int DEFAULT_DISRUPTOR_BUFFER_SIZE = 16384;







}
