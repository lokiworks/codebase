package com.znlh.framework.domain.event.api;

public interface DomainEventStoreProxyHook {
    int preSave(DomainEvent event);

    int postSave(DomainEvent event);

    int postCommit(DomainEvent event);
}
