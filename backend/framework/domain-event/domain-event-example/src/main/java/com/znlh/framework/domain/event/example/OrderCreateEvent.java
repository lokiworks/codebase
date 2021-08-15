package com.znlh.framework.domain.event.example;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderCreateEvent extends OrderEvent<OrderDO> {
    public static final String  EVENT_TYPE = "OrderCreateEvent";
    private OrderDO entity;
    private String eventId;
    private String entityVersion;


    @Override
    public String eventId() {
        return eventId;
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

    @Override
    public OrderDO entity() {
        return entity;
    }

    @Override
    public String entityId() {
        return entity.getOrderId();
    }
}
