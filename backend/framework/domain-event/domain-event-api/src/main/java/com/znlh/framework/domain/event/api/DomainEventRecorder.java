package com.znlh.framework.domain.event.api;


public interface DomainEventRecorder {
    boolean record(DomainEvent event);

}
