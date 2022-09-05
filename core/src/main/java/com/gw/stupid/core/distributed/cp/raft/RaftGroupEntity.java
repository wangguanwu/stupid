package com.gw.stupid.core.distributed.cp.raft;

import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.RaftGroupService;
import com.alipay.sofa.jraft.StateMachine;
import com.gw.stupid.consistency.CpRequestProcessor;
import lombok.Builder;
import lombok.Data;

/**
 * @author guanwu
 * @created on 2022-09-05 15:57:22
 **/

@Data
@Builder
public class RaftGroupEntity {

    private Node node;
    private StateMachine stateMachine;
    private RaftGroupService raftGroupService;
    private CpRequestProcessor requestProcessor;

    public RaftGroupEntity(Node node, StateMachine stateMachine, RaftGroupService raftGroupService, CpRequestProcessor requestProcessor) {
        this.node = node;
        this.stateMachine = stateMachine;
        this.raftGroupService = raftGroupService;
        this.requestProcessor = requestProcessor;
    }

}
