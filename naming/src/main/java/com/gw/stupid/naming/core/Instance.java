package com.gw.stupid.naming.core;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guanwu
 * @created on 2022-07-11 16:01:00
 * 注册节点信息
 **/
public class Instance implements Record{
    private static final long serialVersionUID = 8141225246070799659L;

    public static final String DEFAULT_IP_PORT_DELIMITER = ":";

    private String ip;
    private String instanceId;
    private int port;
    private boolean healthy = true;
    private boolean enabled = true;

    /**
     * 心跳时间
     */
    private volatile long lastBeatMillis = System.currentTimeMillis();


    private String clusterName;

    private String serviceName;

    private Map<String, String> metaInfo = new HashMap<>(2);

    /**
     * 临时节点
     */
    private boolean ephemeral = true;

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    @Override
    public String checkSum() {
        return null;
    }

    @Override
    public void recalculateCheckSum() {

    }

    public String getIpAndPort() {
        return getIpAndPort(DEFAULT_IP_PORT_DELIMITER);
    }

    public String getIpAndPort(String delimiter) {

        if (StringUtils.isEmpty(delimiter)) {
            delimiter = DEFAULT_IP_PORT_DELIMITER;
        }
        return ip + delimiter + port;
    }

    public long getLastBeatMillis() {
        return lastBeatMillis;
    }

    public void setLastBeatMillis(long lastBeatMillis) {
        this.lastBeatMillis = lastBeatMillis;
    }

    public String getInstanceId() {
        return null;
    }
}
