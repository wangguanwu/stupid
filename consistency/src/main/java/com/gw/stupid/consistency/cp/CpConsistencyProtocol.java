package com.gw.stupid.consistency.cp;

import com.gw.stupid.consistency.Config;
import com.gw.stupid.consistency.ConsistencyProtocol;
import com.gw.stupid.consistency.CpRequestProcessor;

/**
 * @author guanwu
 * @created on 2022-08-10 16:19:25
 *
 * 保障CP一致性的协议
 **/
public interface CpConsistencyProtocol<C extends Config<P>, P extends CpRequestProcessor> extends
        ConsistencyProtocol<C, P> {

    /**
     * 判断当前节点是否是给定group的leader
     * @param group
     * @return
     */
    boolean isLeader(String group);
}
