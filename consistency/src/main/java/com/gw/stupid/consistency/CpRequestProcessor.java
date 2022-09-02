package com.gw.stupid.consistency;

import com.gw.stupid.consistency.entity.auto.ReadRequest;
import com.gw.stupid.consistency.entity.auto.Response;
import com.gw.stupid.consistency.entity.auto.WriteRequest;

/**
 * @author guanwu
 * @created on 2022-08-09 10:26:53
 *
 * 一致性协议处理器顶级接口
 **/
public interface CpRequestProcessor {
    Response request(ReadRequest readRequest);

    Response onApply(WriteRequest writeRequest);

    void onError(Throwable error);

    String group();
}
