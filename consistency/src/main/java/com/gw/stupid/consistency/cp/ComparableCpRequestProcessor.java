package com.gw.stupid.consistency.cp;

import com.gw.stupid.consistency.CpRequestProcessor;
import com.gw.stupid.consistency.snapshot.SnapshotOperation;

import java.util.Collections;
import java.util.List;

/**
 * @author guanwu
 * @created on 2022-08-10 17:00:51
 **/
public abstract class ComparableCpRequestProcessor implements CpRequestProcessor, Comparable<ComparableCpRequestProcessor> {


    public  List<SnapshotOperation> getSnapShotOperation() {
        return Collections.emptyList();
    }

    @Override
    public int compareTo(ComparableCpRequestProcessor processor) {
        return this.getClass().getName().compareTo(processor.getClass().getName());
    }
}
