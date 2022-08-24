package com.gw.stupid.core.distributed.cp.raft.processor;

import com.gw.stupid.consistency.Serializer;

/**
 * @author guanwu
 * @created on 2022-08-24 18:39:41
 **/
public abstract class AbstractProcessor {

    private final Serializer serializer;

    protected AbstractProcessor(Serializer serializer) {
        this.serializer = serializer;
    }
}
