package com.gw.stupid.naming.core;

import com.gw.stupid.common.utils.KeyBuilderUtils;
import com.gw.stupid.exception.StupidException;
import com.gw.stupid.naming.consistency.CpService;
import com.gw.stupid.naming.consistency.RecordListener;
import com.gw.stupid.naming.utils.NamingUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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

    @Resource
    private CpService cpService;

    /**
     * 注册实例
     *
     * @param namespaceId
     * @param serviceName
     * @param instance
     */
    public void registerInstance(String namespaceId, String serviceName, Instance instance, boolean isEphemeral) throws StupidException {
        createEmptyServiceForNamespace(namespaceId, serviceName, true);

        Service service = getService(namespaceId, serviceName);

        if (null == service) {
            throw new StupidException(StupidException.INVALID_PARAM, "namespaceId: " + namespaceId + " serviceName: " + serviceName);
        }

        List<Instance> instances = new ArrayList<>();

        instances.add(instance);

        addInstances(namespaceId, serviceName, isEphemeral, instances);
    }

    public void addInstances(String namespaceId, String serviceName, boolean isEphemeral, List<Instance> instances) throws StupidException {
        Service service = getService(namespaceId, serviceName);
        if (service == null) {
            throw new StupidException(StupidException.INVALID_PARAM, namespaceId + ":" + serviceName);
        }
        String key = KeyBuilderUtils.buildInstanceListKey(namespaceId, serviceName, isEphemeral);
        //todo 优化为可重入读写锁
        synchronized (service) {
            List<Instance> toBeAddInstanceList = addInstances(service, isEphemeral, instances);
            Instances instances1 = new Instances();
            instances1.setInstanceList(toBeAddInstanceList);
            cpService.put(key, instances1);
        }
    }

    private List<Instance> addInstances(Service service, boolean isEphemeral, List<Instance> instanceList) {
        return null;
    }


    public Service getService(String nameSpaceId, String serviceName) {
        return selectService(nameSpaceId, serviceName);
    }

    public void createEmptyServiceForNamespace(String namespaceId, String serviceName, boolean isEphemeral) {
         createServiceIfAbsent(namespaceId, serviceName,null, isEphemeral);
    }

    public void createServiceIfAbsent(String namespaceId, String serviceName, Cluster cluster, boolean isEphemeral) {
        Service service = selectService(namespaceId, serviceName);
        if (null != service) {
            return;
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
        putServiceAndInit(service);
        if (isEphemeral) {
            //todo
        }
    }

    private void putServiceAndInit(Service service) {
        String namespaceId = service.getNamespaceId();
        if (coreServiceMap.get(namespaceId) == null) {
            synchronized (coreServiceMap) {
                if (coreServiceMap.get(namespaceId) == null) {
                    coreServiceMap.putIfAbsent(namespaceId, new ConcurrentSkipListMap<>());
                }
            }
        }

        Map<String, Service> services = coreServiceMap.get(namespaceId);

        services.put(service.getNamespaceId(), service);
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
