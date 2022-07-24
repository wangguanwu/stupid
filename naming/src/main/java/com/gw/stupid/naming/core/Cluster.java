package com.gw.stupid.naming.core;

import lombok.Data;

import java.util.List;

/**
 * @author guanwu
 * @created on 2022-07-11 16:02:36
 * 集群
 **/

@Data
public class Cluster implements Record{
    private static final long serialVersionUID = 8695895760781941160L;

    List<Instance> instanceList;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return this.service;
    }

    private String name;

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
