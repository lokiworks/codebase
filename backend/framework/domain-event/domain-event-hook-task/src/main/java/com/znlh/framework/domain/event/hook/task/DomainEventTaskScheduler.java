package com.znlh.framework.domain.event.hook.task;

import com.znlh.framework.domain.event.api.NamedThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DomainEventTaskScheduler {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(2, new NamedThreadFactory("DomainEventTaskScheduler", false));
    private DomainEventTaskServer server;

    public DomainEventTaskScheduler(DomainEventTaskServer server) {
        this.server = server;
        executor.scheduleAtFixedRate(this::run, 30, 30, TimeUnit.SECONDS);
    }

    public void run() {
        //   log.info("Scheduled trigger domain event task backup publish process.");
        server.run();
    }
}
