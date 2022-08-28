package com.gw.stupid.core.distributed.cp.raft;

import com.gw.stupid.api.common.util.JacksonUtils;
import com.gw.stupid.consistency.Config;
import com.gw.stupid.consistency.cp.ComparableCpRequestProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * Raft相关配置
 *
 * @author guanwu
 * @created on 2022-08-22 19:09:40
 **/

@Configuration
@ConfigurationProperties(prefix = "stupid.core.protocol.raft")
public class RaftConfig implements Config<ComparableCpRequestProcessor> {

    private Map<String, String> data = Collections.synchronizedMap(new HashMap<>());

    private Set<String> members = Collections.synchronizedSet(new HashSet<>());

    private String selfAddress;

    public void setData(Map<String, String> data) {
        this.data = Collections.synchronizedMap(data);
    }

    public Map<String, String> getData() {
        return data;
    }

    @Override
    public void setMembers(String self, Set<String> members) {
        this.selfAddress = self;
        this.members.clear();
        ;
        this.members.addAll(members);
    }

    @Override
    public void addMembers(Set<String> members) {
        this.members.addAll(members);
    }

    @Override
    public void removeMembers(Set<String> members) {
        this.members.removeAll(members);
    }

    @Override
    public String getSelfMember() {
        return selfAddress;
    }

    @Override
    public void setValue(String key, String value) {
        data.put(key, value);
    }

    @Override
    public String getValue(String key) {
        return data.get(key);
    }

    @Override
    public String getOrDefault(String key, String defaultValue) {
        return data.getOrDefault(key, defaultValue);
    }

    public Set<String> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        try {
            return JacksonUtils.toJsonString(data);
        } catch (Exception ignored) {
        }
        return "";
    }


}
