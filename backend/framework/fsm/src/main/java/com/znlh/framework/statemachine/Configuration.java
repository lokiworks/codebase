package com.znlh.framework.statemachine;

public interface Configuration {
    String getConfig(String dataId, long timeoutMills);
    String getConfig(String dataId);
    void addConfigListener(String dataId, ConfigurationChangeListener listener);
}
