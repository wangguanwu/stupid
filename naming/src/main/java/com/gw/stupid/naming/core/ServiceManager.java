package com.gw.stupid.naming.core;

import com.gw.stupid.naming.consistency.RecordListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 管理服务
 * 服务注册容器
 */

@Component
public class ServiceManager implements RecordListener<Service> {

    /**
     *  namespaceId, group:serviceName
     */
    private Map<String, Map<String, Service>> serviceMap;

    /**
     * 注册实例
     * @param namespaceId
     * @param serviceName
     * @param instance
     */
    public void registerInstance(String namespaceId, String serviceName, Instance instance) {

    }

    @Override
    public void onChange(String key, Service value) {

    }

    @Override
    public void onDelete(String key) {

    }
}
