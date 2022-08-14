package com.znlh.framework.domain.event.hook.task;

import com.znlh.framework.domain.event.api.DomainEvent;

public interface DomainEventTask {
    void run(DomainEvent event);
}
