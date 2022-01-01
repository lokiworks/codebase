package com.znlh.framework.domain.event.hook.task;

import com.znlh.framework.domain.event.api.DomainEvent;


public interface DomainEventTaskInvoker {
    Object invoke(String bean, DomainEvent event);
}
