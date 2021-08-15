package com.znlh.framework.domain.event.producer;

import com.znlh.framework.domain.event.api.DomainEvent;
import lombok.Data;


@Data
 class DomainEventImpl implements DomainEvent {
    private String eventId;
    private String eventType;
    private Object entity;
    private String entityId;
    private String entityType;
    private String entityVersion;

    @Override
    public String eventId() {
        return eventId;
    }

    @Override
    public String eventType() {
        return eventType;
    }

    @Override
    public Object entity() {
        return entity;
    }

    @Override
    public String entityId() {
        return entityId;
    }

    @Override
    public String entityType() {
        return entityType;
    }

   public String entityVersion(){
        return entityVersion;
    }
}
