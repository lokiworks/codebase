package com.znlh.framework.domain.event.hook.task;

import com.znlh.framework.domain.event.api.DomainEvent;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

public class SpringBeanDomainEventTaskInvoker implements DomainEventTaskInvoker {
    private ApplicationContext context;

    public SpringBeanDomainEventTaskInvoker(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object invoke(String bean, DomainEvent event) {
        if (Objects.isNull(context)) {
            return null;
        }

        if (Objects.isNull(bean)) {
            return null;
        }

        Object obj = context.getBean(bean);
        if (Objects.isNull(obj)) {
            return null;
        }

        if (obj instanceof DomainEventTask) {
            DomainEventTask task = (DomainEventTask) obj;
            task.run(event);
        }
        return null;
    }
}
