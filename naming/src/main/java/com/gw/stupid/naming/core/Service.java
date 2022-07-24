package com.gw.stupid.naming.core;

import com.gw.stupid.common.validate.Validatable;
import com.gw.stupid.naming.consistency.RecordListener;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guanwu
 * @created on 2022-07-11 16:03:07
 * 服务 (ClusterName:Cluster)
 **/

@Data
public class Service implements Record, RecordListener<Instances> , Validatable {

    private static final String SERVICE_NAME_RULES = "[0-9a-zA-Z@\\.:_-]+";


    private static final long serialVersionUID = 3557647373400773712L;

    private Map<String, Cluster> clusterMap = new HashMap<>();

    private String serviceName;

    private String namespaceId;

    private String appName;

    private String group;

    private Map<String, Object> metaMap;

    private volatile long lastUpdateTimeMillis = 0;

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

    @Override
    public String toString() {
        return "Service{" +
                "clusterMap=" + clusterMap +
                ", serviceName='" + serviceName + '\'' +
                ", namespaceId='" + namespaceId + '\'' +
                ", appName='" + appName + '\'' +
                ", groupName='" + group + '\'' +
                ", metaMap=" + metaMap +
                ", lastUpdateTimeMillis=" + lastUpdateTimeMillis +
                '}';
    }

    @Override
    public void validate() {

    }
}
