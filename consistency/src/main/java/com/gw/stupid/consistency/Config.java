package com.gw.stupid.consistency;

import java.io.Serializable;
import java.util.Set;

/**
 * 一致性协议相关的配置类
 * @author guanwu
 * @created on 2022-08-09 14:19:04
 **/
public interface Config<P extends CpRequestProcessor> extends Serializable {

    void setMembers(String self, Set<String> members);

    void addMembers(Set<String> members);

    void removeMembers(Set<String> members);

    String getSelfMember();

    void setValue(String key, String value);

    String getValue(String key);

    String getOrDefault(String key, String defaultValue);
}
