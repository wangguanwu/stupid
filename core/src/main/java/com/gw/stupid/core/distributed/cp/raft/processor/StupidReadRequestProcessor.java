package com.gw.stupid.core.distributed.cp.raft.processor;

import com.alipay.sofa.jraft.rpc.RpcContext;
import com.alipay.sofa.jraft.rpc.RpcProcessor;
import com.gw.stupid.consistency.Serializer;
import com.gw.stupid.consistency.entity.auto.ReadRequest;
import com.gw.stupid.core.distributed.cp.raft.JRaftServer;


/**
 * @author guanwu
 * @created on 2022-08-24 17:06:09
 **/
public class StupidReadRequestProcessor extends AbstractProcessor implements RpcProcessor<ReadRequest> {

    private final JRaftServer server;

    public StupidReadRequestProcessor(JRaftServer server,Serializer serializer) {
        super(serializer);
        this.server = server;
    }


    @Override
    public void handleRequest(RpcContext rpcCtx, ReadRequest request) {

    }

    @Override
    public String interest() {
        return ReadRequest.class.getName();
    }


}
