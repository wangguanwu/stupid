package com.gw.stupid.naming.core;

import java.util.List;

/**
 * @author guanwu
 * @created on 2022-07-11 16:02:36
 * 集群
 **/
public class Cluster implements Record{
    private static final long serialVersionUID = 8695895760781941160L;

    List<Instance> instanceList;

    public Cluster(List<Instance> instanceList) {
        this.instanceList = instanceList;
    }

    public Cluster() {
    }

    public void setInstanceList(List<Instance> instanceList) {
        this.instanceList = instanceList;
    }

    @Override
    public String checkSum() {
        return null;
    }

    @Override
    public void recalculateCheckSum() {

    }
}
