package com.znlh.framework.domain.event.api;


public interface DomainEventStoreProxy {
    int save(DomainEvent event);
}
