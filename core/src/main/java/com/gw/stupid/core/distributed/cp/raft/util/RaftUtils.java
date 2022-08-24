package com.gw.stupid.core.distributed.cp.raft.util;

import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.rpc.RpcServer;
import com.alipay.sofa.jraft.rpc.impl.GrpcRaftRpcFactory;
import com.alipay.sofa.jraft.util.RpcFactoryHelper;
import com.gw.stupid.core.distributed.cp.raft.JRaftServer;

/**
 * @author guanwu
 * @created on 2022-08-24 16:36:01
 **/
public class RaftUtils {

    public static RpcServer createAndInitRpcServer(JRaftServer jRaftServer, PeerId peerId) {
        GrpcRaftRpcFactory grpcRaftRpcFactory = (GrpcRaftRpcFactory) RpcFactoryHelper.rpcFactory();

        grpcRaftRpcFactory.registerProtobufSerializer();


    }
}
