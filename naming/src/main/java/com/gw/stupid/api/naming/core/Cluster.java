package com.gw.stupid.api.naming.core;

import com.gw.stupid.common.ExceptionsHelper;
import com.gw.stupid.common.constant.NamingConstants;
import com.gw.stupid.common.validate.Validatable;
import com.gw.stupid.api.enums.ApiErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guanwu
 * @created on 2022-07-11 16:02:36
 * 集群
 **/

@Data
public class Cluster implements Record, Validatable {

    private static final long serialVersionUID = 8695895760781941160L;

    private volatile Set<Instance> consistenceInstances;

    private volatile Set<Instance> ephemeralInstances;

    private Service service;

    private Map<String, String> metaData = new ConcurrentHashMap<>();

    private volatile boolean isInitialized = false;

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return this.service;
    }

    private String name;

    public Cluster() {
    }

    public Cluster(Service service, String name) {
        this.service = service;
        this.name = name;
    }

    /**
     * 获取集群的所有注册实例
     * @param ephemeral
     * @return
     */
    public List<Instance> allInstance(boolean ephemeral) {
        if (ephemeral) {
            return new LinkedList<>(ephemeralInstances);
        }
        return new LinkedList<>(consistenceInstances);
    }



    @Override
    public String checkSum() {
        return null;
    }

    @Override
    public void recalculateCheckSum() {

    }

    @Override
    public void validate() {

        if (StringUtils.isEmpty(getName())) {
            throw ExceptionsHelper.createRuntimeException(ApiErrorCodeEnum.ARGS_INVALID);
        }

        if (getName().matches(NamingConstants.CLUSTER_NAME_RULES)) {
            throw ExceptionsHelper.createRuntimeException(ApiErrorCodeEnum.ARGS_INVALID);
        }
    }

    public void init() {
        //todo 执行初始化方法
    }
}
