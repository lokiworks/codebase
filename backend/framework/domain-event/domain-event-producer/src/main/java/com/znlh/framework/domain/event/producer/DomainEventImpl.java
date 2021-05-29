package com.znlh.framework.domain.event.producer;

import com.znlh.framework.domain.event.api.DomainEntity;
import com.znlh.framework.domain.event.api.DomainEvent;
import lombok.Data;


@Data
 class DomainEventImpl implements DomainEvent {
    private String eventId;
    private String eventType;
    private DomainEntity entity;


    @Override
    public String eventId() {
        return eventId;
    }

    @Override
    public String eventType() {
        return eventType;
    }

    @Override
    public DomainEntity entity() {
        return entity;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEntity(DomainEntity entity) {
        this.entity = entity;
    }
}
