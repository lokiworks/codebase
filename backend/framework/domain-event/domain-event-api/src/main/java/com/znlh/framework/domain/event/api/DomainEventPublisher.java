package com.znlh.framework.domain.event.api;


public interface DomainEventPublisher {
    void publish(DomainEvent event);
    void batchPublish(int size);
    void batchPublish();
}
