package com.znlh.framework.statemachine;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = StateMachineConstants.STATE_MACHINE_PREFIX)
public class StateMachineProperties {
    private boolean enabled = true;
    private String address;
    private String namespaceId;
    private String group;
    private Integer timeout = 30 * 1000;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
