package com.znlh.framework.domain.event.example;

import com.znlh.framework.domain.event.api.DomainEvent;
import lombok.Data;

@Data
public abstract  class OrderEvent<T> implements DomainEvent<T> {

    public static final String ORDER_ENTITY_TYPE ="ORDER";


    @Override
    public String entityType() {
        return ORDER_ENTITY_TYPE;
    }
}
