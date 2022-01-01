package com.znlh.framework.domain.event.hook.task;

import java.util.Objects;

public class DomainEventTaskInstanceHelper {
    private DomainEventTaskInstanceHelper() {
    }

    public static DomainEventTaskInstance from(DomainEventTaskDefinition definition) {
        if (Objects.isNull(definition)) {
            return null;
        }
        DomainEventTaskInstance instance = new DomainEventTaskInstance();
        instance.setEventType(definition.getEventType());
        instance.setBean(definition.getBean());
        return instance;
    }
}
