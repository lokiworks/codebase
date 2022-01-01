package com.znlh.framework.domain.event.hook.task;

import java.util.List;

public interface DomainEventTaskStore {
    List<DomainEventTaskDefinition> queryDefinition(String eventType);

    int batchSave(List<DomainEventTaskInstance> instances);

    List<DomainEventTaskInstance> queryInstance(int size);
}
