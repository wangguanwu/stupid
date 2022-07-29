package com.gw.stupid.naming.core;

import com.gw.stupid.common.utils.KeyBuilderUtils;
import com.gw.stupid.exception.StupidException;
import com.gw.stupid.naming.consistency.CpService;
import com.gw.stupid.naming.consistency.Datum;
import com.gw.stupid.naming.consistency.RecordListener;
import com.gw.stupid.naming.core.enums.InstanceOperation;
import com.gw.stupid.naming.utils.NamingUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * 这个方法实际上没有改变注册表里面的结构，只是复制了一份数据放到一个新创建的list里，基于这个list修改
     * 利用Copy-On-Write思想提升并发性能；
     * 如果是保证CP，那就只能走完一致性协议算法的流程才能更新注册表的结构，以确保CP性质
     * @param service
     * @param isEphemeral
     * @param op
     * @return
     */
    public List<Instance> getUpdateInstance(Service service, boolean isEphemeral, InstanceOperation op)
    throws StupidException {

        Datum<Instances> instancesDatum = (Datum<Instances>) cpService.get(KeyBuilderUtils.buildInstanceListKey(service.getNamespaceId(),
                service.getServiceName(), isEphemeral));

        List<Instance> currentInstances = service.allInstances(isEphemeral);

        Map<String, Instance> currentInstanceMap = new HashMap<>(currentInstances.size());

        for (Instance currentInstance : currentInstances) {
            currentInstanceMap.put(currentInstance.getIpAndPort(), currentInstance);
        }

        return null;


    }

    private Map<String, Instance> updateInstancesAndGet(Map<String, Instance> currentInstance,
                                      List<Instance> toBeUpdateInstances) {

        Map<String, Instance> instanceMap = new HashMap<>(toBeUpdateInstances.size());

        for (Instance toBeUpdateInstance : toBeUpdateInstances) {

            Instance currIp = currentInstance.get(toBeUpdateInstance.getIpAndPort());

            if (currIp != null) {

                toBeUpdateInstance.setHealthy(currIp.isHealthy());

                toBeUpdateInstance.setLastBeatMillis(currIp.getLastBeatMillis());

                //todo 放置到InstanceMap
            }
        }

        return instanceMap;
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
