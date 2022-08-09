package com.gw.stupid.consistency;

/**
 * @author guanwu
 * @created on 2022-08-08 16:44:26
 *
 * 一致性协议
 **/
public interface ConsistencyProtocol<C extends Config, P extends CpRequestProcessor> extends ConsistencyCommand {


}
