package com.gw.stupid.api.naming.core;

import com.gw.stupid.common.constant.NamingConstants;
import com.gw.stupid.common.validate.Validatable;
import com.gw.stupid.api.exception.StupidException;
import com.gw.stupid.api.exception.runtime.StupidRuntimeException;
import com.gw.stupid.api.naming.consistency.RecordListener;
import lombok.Data;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    private ReadWriteLock lock =  new ReentrantReadWriteLock();

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

    public boolean tryGetWriteLock(long time, TimeUnit timeUnit) throws InterruptedException {
        return lock.writeLock().tryLock(time, timeUnit);
    }

    public boolean tryGetReadLock(long time, TimeUnit timeUnit) throws InterruptedException {
        return lock.readLock().tryLock(time, timeUnit);
    }

    public void unlockWriteLock() {
        lock.writeLock().unlock();
    }

    public void unlockReadLock() {
        lock.readLock().unlock();
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

    public List<Instance> allInstances(boolean ephemeral) {

        List<Instance> allInstances = new LinkedList<>();

        for (Map.Entry<String, Cluster> clusterEntry : clusterMap.entrySet()) {

            allInstances.addAll(clusterEntry.getValue().allInstance(ephemeral));

        }
        return allInstances;
    }

    @Override
    public void validate() {

        if (getServiceName() == null) {
            throw new StupidRuntimeException(StupidException.INVALID_PARAM, "serviceName is null");
        }

        if (!getServiceName().matches(NamingConstants.SERVICE_NAME_RULES)) {
            throw new StupidRuntimeException(StupidException.INVALID_PARAM, "serviceName非法: " + NamingConstants.SERVICE_NAME_RULES);
        }

        for (Validatable validatable : getClusterMap().values()) {
            validatable.validate();
        }
    }
}
