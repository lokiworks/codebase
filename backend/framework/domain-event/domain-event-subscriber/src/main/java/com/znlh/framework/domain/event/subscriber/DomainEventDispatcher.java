package com.znlh.framework.domain.event.subscriber;

import com.znlh.framework.domain.event.api.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class DomainEventDispatcher {
    private Map<String, List<DomainEventSubscribeHandler>> maps = new ConcurrentHashMap<>();

    private DomainEventDispatcher() {
    }

    private static final DomainEventDispatcher INSTANCE = new DomainEventDispatcher();

    public void register(String eventType, DomainEventSubscribeHandler handler) {
        maps.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public static DomainEventDispatcher getInstance() {
        return INSTANCE;
    }

    public void dispatch(DomainEvent event) {
        String eventType = event.eventType();
        List<DomainEventSubscribeHandler> handlers = maps.get(eventType);
        if (Objects.isNull(handlers) || handlers.isEmpty()){
            return;
        }

        for (DomainEventSubscribeHandler handler: handlers){
            handler.invoke(event);
        }
    }


}
