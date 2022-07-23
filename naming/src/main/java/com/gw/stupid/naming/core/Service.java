package com.gw.stupid.naming.core;

import com.gw.stupid.naming.consistency.RecordListener;

import java.util.Map;

/**
 * @author guanwu
 * @created on 2022-07-11 16:03:07
 * 服务 (ClusterName:Cluster)
 **/
public class Service implements Record, RecordListener<Instances> {

    private static final long serialVersionUID = 3557647373400773712L;

    private Map<String, Cluster> clusterMap;


    @Override
    public String checkSum() {
        return null;
    }

    @Override
    public void recalculateCheckSum() {

    }

    @Override
    public void onChange(String key, Instances value) {

    }

    @Override
    public void onDelete(String key) {

    }
}
