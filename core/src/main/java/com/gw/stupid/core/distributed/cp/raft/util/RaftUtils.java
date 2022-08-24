package com.gw.stupid.core.distributed.cp.raft.util;

import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.rpc.RaftRpcServerFactory;
import com.alipay.sofa.jraft.rpc.RpcServer;
import com.alipay.sofa.jraft.rpc.impl.GrpcRaftRpcFactory;
import com.alipay.sofa.jraft.rpc.impl.MarshallerRegistry;
import com.alipay.sofa.jraft.util.RpcFactoryHelper;
import com.gw.stupid.consistency.SerializerFactory;
import com.gw.stupid.consistency.entity.auto.ReadRequest;
import com.gw.stupid.consistency.entity.auto.Response;
import com.gw.stupid.consistency.entity.auto.WriteRequest;
import com.gw.stupid.core.distributed.cp.raft.JRaftServer;
import com.gw.stupid.core.distributed.cp.raft.processor.StupidReadRequestProcessor;
import com.gw.stupid.core.distributed.cp.raft.processor.StupidWriteRequestProcessor;

/**
 * @author guanwu
 * @created on 2022-08-24 16:36:01
 **/
public class RaftUtils {

    public static RpcServer createAndInitRpcServer(JRaftServer jRaftServer, PeerId peerId) {
        GrpcRaftRpcFactory grpcRaftRpcFactory = (GrpcRaftRpcFactory) RpcFactoryHelper.rpcFactory();

        grpcRaftRpcFactory.registerProtobufSerializer(ReadRequest.class.getName(), ReadRequest.getDefaultInstance());

        grpcRaftRpcFactory.registerProtobufSerializer(WriteRequest.class.getName(), WriteRequest.getDefaultInstance());

        grpcRaftRpcFactory.registerProtobufSerializer(Response.class.getName(), Response.getDefaultInstance());

        MarshallerRegistry marshallerRegistry = grpcRaftRpcFactory.getMarshallerRegistry();

        marshallerRegistry.registerResponseInstance(Response.class.getName(), Response.getDefaultInstance());

        marshallerRegistry.registerResponseInstance(ReadRequest.class.getName(), ReadRequest.getDefaultInstance());

        marshallerRegistry.registerResponseInstance(WriteRequest.class.getName(), WriteRequest.getDefaultInstance());

        RpcServer rpcServer = grpcRaftRpcFactory.createRpcServer(peerId.getEndpoint());

        RaftRpcServerFactory.addRaftRequestProcessors(rpcServer, RaftExecutors.getRaftCoreExecutor(),
                RaftExecutors.getRaftCliServiceExecutor());

        //注册业务处理器
        rpcServer.registerProcessor(new StupidReadRequestProcessor(jRaftServer, SerializerFactory.getDefault()));
        rpcServer.registerProcessor(new StupidWriteRequestProcessor(jRaftServer, SerializerFactory.getDefault()));

        return rpcServer;
    }
}
