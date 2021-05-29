package com.znlh.framework.domain.event.producer;

import com.alibaba.fastjson.JSON;
import com.znlh.framework.domain.event.api.DomainEntity;
import lombok.Data;


import java.util.Objects;
@Data
 class DomainEntityImpl implements DomainEntity {
    private String version;
    private String entityType;
    private String entityId;
    private String entityData;


    @Override
    public Object getData() {
        return JSON.parse(entityData);
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public String entityType() {
        return entityType;
    }

    @Override
    public String entityId() {
        return entityId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setEntityData(String entityData) {
        this.entityData = entityData;
    }
}
