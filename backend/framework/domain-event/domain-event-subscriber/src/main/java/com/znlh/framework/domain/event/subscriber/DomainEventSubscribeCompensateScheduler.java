package com.znlh.framework.domain.event.subscriber;


import com.znlh.framework.domain.event.api.DomainEvent;
import com.znlh.framework.domain.event.api.DomainEventRetriever;
import com.znlh.framework.domain.event.api.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DomainEventSubscribeCompensateScheduler {
    private List<DomainEventRetriever> retrievers;
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(2, new NamedThreadFactory("DomainEventSubscribeCompensateScheduler", false));

    public DomainEventSubscribeCompensateScheduler(List<DomainEventRetriever> retrievers) {
        this.retrievers = retrievers;
        executor.scheduleAtFixedRate(this::run, 5, 5, TimeUnit.MINUTES);
    }

    public void run() {
        log.info("Scheduled trigger domain event subscribe compensate process.");
        if (Objects.isNull(retrievers) || retrievers.isEmpty()) {
            return;
        }
        for (DomainEventRetriever retriever : retrievers) {
            List<DomainEvent> domainEvents = retriever.batchRetrieve();
            if (Objects.isNull(domainEvents) || domainEvents.isEmpty()) {
                continue;
            }

            for (DomainEvent event : domainEvents) {
                DomainEventDispatcher.getInstance().dispatch(event);
            }
        }
    }
}
