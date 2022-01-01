package com.znlh.framework.domain.event.hook.task;

import java.util.List;
import java.util.Objects;

public class DomainEventTaskServer {

    DomainEventTaskStore store;
    DomainEventTaskInvoker taskInvoker;

    public void run() {
        List<DomainEventTaskInstance> instances = store.queryInstance(30);
        if (Objects.isNull(instances)) {
            return;
        }

        for (DomainEventTaskInstance instance : instances) {
            String bean = instance.getBean();
            taskInvoker.invoke(instance.getBean(), null);
        }
    }
}
