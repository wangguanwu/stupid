package com.gw.stupid.consistency.cp;

import com.gw.stupid.consistency.CpRequestProcessor;
import com.gw.stupid.consistency.snapshot.SnapshotOperation;

import java.util.Collections;
import java.util.List;

/**
 * @author guanwu
 * @created on 2022-08-10 17:00:51
 **/
public abstract class AbstractCpRequestProcessor implements CpRequestProcessor, Comparable<AbstractCpRequestProcessor> {


    public  List<SnapshotOperation> getSnapShotOperation() {
        return Collections.emptyList();
    }

    @Override
    public int compareTo(AbstractCpRequestProcessor processor) {
        return this.getClass().getName().compareTo(processor.getClass().getName());
    }
}
