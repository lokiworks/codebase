package com.znlh.framework.statemachine;

public class ConfigurationChangeEvent {
    private String dataId;
    private String value;

    public ConfigurationChangeEvent(String dataId,String value){
        this.dataId = dataId;
        this.value = value;
    }

    public String getDataId() {
        return dataId;
    }

    public String getValue() {
        return value;
    }
}
