package com.znlh.framework.domain.event.producer;

import com.znlh.framework.domain.event.api.DomainEventPublisher;
import com.znlh.framework.domain.event.api.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
public class DomainEventPublishScheduler {
    private DomainEventPublisher publisher;
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(2, new NamedThreadFactory("DomainEventPublishScheduler", false));
    public DomainEventPublishScheduler(DomainEventPublisher publisher){
        this.publisher = publisher;
        executor.scheduleAtFixedRate(this::run, 5, 5, TimeUnit.MINUTES);
    }
    public void run(){
        log.info("Scheduled trigger domain event backup publish process.");
        publisher.batchPublish();
    }
}
