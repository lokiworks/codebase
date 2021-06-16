package com.znlh.framework.statemachine.impl;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractSharedListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.znlh.framework.statemachine.Configuration;
import com.znlh.framework.statemachine.ConfigurationChangeEvent;
import com.znlh.framework.statemachine.ConfigurationChangeListener;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NacosConfiguration implements Configuration {
    private String address;
    private String namespaceId;
    private String group;
    private Integer timeout;

    private ConfigService configService;

    private ConcurrentMap<String, ConcurrentMap<ConfigurationChangeListener, NacosListener>> configListenersMap
            = new ConcurrentHashMap<>(8);

    public void init() {
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, address);
            properties.put(PropertyKeyConst.NAMESPACE, namespaceId);
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getConfig(String dataId, long timeoutMills) {
        try {
            return configService.getConfig(dataId, group, timeoutMills);
        } catch (NacosException e) {
            // print logging
            return null;
        }
    }

    @Override
    public String getConfig(String dataId) {
        try {
            return configService.getConfig(dataId, group, timeout);
        } catch (NacosException e) {
            // print logging
            return null;
        }
    }

    @Override
    public void addConfigListener(String dataId, ConfigurationChangeListener listener) {
        if (Objects.isNull(dataId) || dataId.isEmpty() || Objects.isNull(listener)) {
            return;
        }
        try {
            NacosListener nacosListener = new NacosListener(dataId, listener);

            configListenersMap.computeIfAbsent(dataId, key -> new ConcurrentHashMap<>())
                    .put(listener, nacosListener);
            configService.addListener(dataId, group, nacosListener);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

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

    public static class NacosListener extends AbstractSharedListener {
        private final String dataId;
        private final ConfigurationChangeListener listener;

        /**
         * Instantiates a new Nacos listener.
         *
         * @param dataId   the data id
         * @param listener the listener
         */
        public NacosListener(String dataId, ConfigurationChangeListener listener) {
            this.dataId = dataId;
            this.listener = listener;
        }

        /**
         * Gets target listener.
         *
         * @return the target listener
         */
        public ConfigurationChangeListener getTargetListener() {
            return this.listener;
        }

        @Override
        public void innerReceive(String dataId, String group, String configInfo) {
            ConfigurationChangeEvent event = new ConfigurationChangeEvent(dataId, configInfo);
            listener.onChangeEvent(event);
        }
    }
}
