package com.gw.stupid.naming.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guanwu
 * @created on 2022-07-11 16:01:00
 * 注册节点信息
 **/
public class InstanceNode implements Record{
    private static final long serialVersionUID = 8141225246070799659L;

    private String ip;
    private String instanceId;
    private int port;
    private boolean healthy = true;
    private boolean enabled = true;
    /**
     * 临时节点
     */
    private String clusterName;
    private String serviceName;
    private Map<String, String> metaData = new HashMap<>(2);
    private boolean ephemeral = true;

    @Override
    public String checkSum() {
        return null;
    }

    @Override
    public void recalculateCheckSum() {

    }
}
