package com.gw.stupid.core.distributed.cp.raft.processor;

import com.alipay.sofa.jraft.rpc.RpcContext;
import com.alipay.sofa.jraft.rpc.RpcProcessor;
import com.gw.stupid.consistency.Serializer;
import com.gw.stupid.consistency.entity.auto.WriteRequest;
import com.gw.stupid.core.distributed.cp.raft.JRaftServer;


/**
 * @author guanwu
 * @created on 2022-08-24 17:06:23
 **/
public class StupidWriteRequestProcessor extends AbstractProcessor implements RpcProcessor<WriteRequest> {


    private final JRaftServer server;

    public StupidWriteRequestProcessor(JRaftServer server, Serializer serializer) {
        super(serializer);
        this.server = server;
    }

    @Override
    public void handleRequest(RpcContext rpcCtx, WriteRequest request) {

    }

    @Override
    public String interest() {
        return WriteRequest.class.getName();
    }
}
