package com.znlh.framework.domain.event.example;

import com.znlh.framework.domain.event.api.DomainEntity;
import com.znlh.framework.domain.event.api.DomainEvent;


public class OrderCreateEvent implements DomainEvent {
    public static final String  EVENT_TYPE = "OrderCreateEvent";
    @Override
    public String eventId() {
        return null;
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

    @Override
    public DomainEntity entity() {
        return null;
    }
}
