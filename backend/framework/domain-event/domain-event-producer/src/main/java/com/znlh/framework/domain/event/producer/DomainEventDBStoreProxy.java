package com.znlh.framework.domain.event.producer;

import com.znlh.framework.domain.event.api.*;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class DomainEventDBStoreProxy implements DomainEventStoreProxy {
    private DomainEventStore store;
    private DomainEventPublisher publisher;
    private ExecutorService executor;
    private List<DomainEventStoreProxyHook> hooks;


    public DomainEventDBStoreProxy(DomainEventStore store, DomainEventPublisher publisher, List<DomainEventStoreProxyHook> hooks) {
        this.store = store;
        this.publisher = publisher;
        this.hooks = hooks;
        executor = Executors.newFixedThreadPool(2, new NamedThreadFactory("DomainEventDBStoreProxy", false));

    }

    @Override
    public int save(DomainEvent event) {
        Optional.ofNullable(hooks).orElse(new ArrayList<>()).stream().filter(Objects::nonNull).forEach(hook -> hook.preSave(event));
        int ret = store.save(event);
        Optional.ofNullable(hooks).orElse(new ArrayList<>()).stream().filter(Objects::nonNull).forEach(hook -> hook.postSave(event));
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    publisher.publish(event);
                    Optional.ofNullable(hooks).orElse(new ArrayList<>()).stream().filter(Objects::nonNull)
                            .forEach(hook -> hook.postCommit(event));

                }
            });
        } else {
            executor.submit(() -> {
                publisher.publish(event);
                Optional.ofNullable(hooks).orElse(new ArrayList<>()).stream().filter(Objects::nonNull)
                        .forEach(hook -> hook.postCommit(event));
            });

        }
        return ret;
    }
}
