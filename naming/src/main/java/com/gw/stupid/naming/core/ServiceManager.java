package com.gw.stupid.naming.core;

import com.gw.stupid.naming.consistency.RecordListener;
import com.gw.stupid.naming.utils.NamingUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 管理服务
 * 服务注册容器
 */

@Component
public class ServiceManager implements RecordListener<Service>{

    /**
     * namespaceId, group:serviceName,service
     */
    private ConcurrentHashMap<String, Map<String, Service>> coreServiceMap;

    /**
     * 注册实例
     *
     * @param namespaceId
     * @param serviceName
     * @param instance
     */
    public void registerInstance(String namespaceId, String serviceName, Instance instance) {
    }

    public Service createEmptyServiceForNamespace(String namespaceId, String serviceName, boolean isEphemeral) {
        return createServiceIfAbsent(namespaceId, serviceName,null, isEphemeral);
    }

    public Service createServiceIfAbsent(String namespaceId, String serviceName, Cluster cluster, boolean isEphemeral) {
        Service service = selectService(namespaceId, serviceName);
        if (null != service) {
            return service;
        }
        service = new Service();
        service.setServiceName(serviceName);
        service.setNamespaceId(namespaceId);
        service.setGroup(NamingUtils.parseGroup(serviceName));
        service.setAppName(NamingUtils.parseApp(serviceName));
        service.setLastUpdateTimeMillis(System.currentTimeMillis());
        service.validate();
        if (null != cluster) {
            cluster.setService(service);
            service.getClusterMap().put(cluster.getName(), cluster);
        }
        Service result = putServiceAndInit(service);
        if (isEphemeral) {
            //todo
        }
        return result;
    }

    private Service putServiceAndInit(Service service) {
        String namespaceId = service.getNamespaceId();
        if (coreServiceMap.get(namespaceId) == null) {
            synchronized (coreServiceMap) {
                if (coreServiceMap.get(namespaceId) == null) {
                    coreServiceMap.putIfAbsent(namespaceId, new ConcurrentSkipListMap<>());
                }
            }
        }

        Map<String, Service> services = coreServiceMap.get(namespaceId);

    }

    public Service selectService(String namespaceId, String serviceName) {
        if (!coreServiceMap.containsKey(namespaceId)) {
            return null;
        }
        return getServiceMap(namespaceId).get(serviceName);
    }

    public Map<String, Service> getServiceMap(String namespaceId) {
        return coreServiceMap.get(namespaceId);
    }


    @Override
    public void onChange(String key, Service value) {

    }

    @Override
    public void onDelete(String key) {

    }
}
