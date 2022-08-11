package com.gw.stupid.consistency.cp;

/**
 * @author guanwu
 * @created on 2022-08-10 16:43:35
 **/
public interface MetadataKey {
    String LEADER = "leader";

    String TERM = "term";

    String GROUP_MEMBER = "raftGroupMember";

    String ERR_MSG = "errMsg";
}
