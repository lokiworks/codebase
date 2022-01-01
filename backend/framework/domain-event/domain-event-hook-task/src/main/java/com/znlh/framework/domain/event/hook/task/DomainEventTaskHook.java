package com.znlh.framework.domain.event.hook.task;

import com.znlh.framework.domain.event.api.DomainEvent;
import com.znlh.framework.domain.event.api.DomainEventStoreProxyHook;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DomainEventTaskHook implements DomainEventStoreProxyHook {
    DomainEventTaskStore store;
    ExpressionParser parser;

    public DomainEventTaskHook(DomainEventTaskStore store) {
        this.store = store;
    }

    @Override
    public int preSave(DomainEvent event) {
        if (Objects.isNull(event)) {
            return -1;
        }

        String eventType = event.eventType();
        if (Objects.isNull(event)) {
            return -1;
        }
        List<DomainEventTaskDefinition> taskDefinitions = store.queryDefinition(eventType);
        if (Objects.isNull(taskDefinitions)) {
            return 0;
        }
        taskDefinitions = taskDefinitions.stream().filter(x -> {
            String condition = x.getCondition();
            if (Objects.nonNull(condition)) {
                Expression exp = parser.parseExpression(condition);
                return exp.getValue(event, Boolean.class);
            }
            return true;
        }).collect(Collectors.toList());


        List<DomainEventTaskInstance> taskInstances =
                taskDefinitions.stream().filter(Objects::nonNull).map(DomainEventTaskInstanceHelper::from).collect(
                        Collectors.toList());
        if (Objects.isNull(taskInstances)) {
            return 0;
        }
        store.batchSave(taskInstances);
        return 0;
    }

    @Override
    public int postSave(DomainEvent event) {
        return 0;
    }

    @Override
    public int postCommit(DomainEvent event) {
        return 0;
    }
}
