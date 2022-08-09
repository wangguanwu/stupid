package com.gw.stupid.consistency;

import com.gw.stupid.consistency.entity.generator.CpResponse;
import com.gw.stupid.consistency.entity.generator.ReadRequest;
import com.gw.stupid.consistency.entity.generator.WriteRequest;

/**
 * @author guanwu
 * @created on 2022-08-09 10:26:53
 *
 * 一致性协议处理器顶级接口
 **/
public interface CpRequestProcessor {
    CpResponse request(ReadRequest readRequest);

    CpResponse onApply(WriteRequest writeRequest);

    void onError(Throwable error);

    String group();
}