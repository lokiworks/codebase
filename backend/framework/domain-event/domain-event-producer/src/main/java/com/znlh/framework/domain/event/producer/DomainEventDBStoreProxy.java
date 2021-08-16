package com.znlh.framework.domain.event.producer;

import com.znlh.framework.domain.event.api.*;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


 class DomainEventDBStoreProxy implements DomainEventStoreProxy {
    private DomainEventStore store;
    private DomainEventPublisher publisher;
    private ExecutorService executor;


    public DomainEventDBStoreProxy(DomainEventStore store, DomainEventPublisher publisher){
        this.store = store;
        this.publisher = publisher;
        executor = Executors.newFixedThreadPool(2,new NamedThreadFactory("DomainEventDBStoreProxy", false));

    }

    @Override
    public int save(DomainEvent event) {
      int ret =   store.save(event);
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    publisher.publish(event);
                }
            });
        } else {
            executor.submit(()->{
                publisher.publish(event);
            });

        }
        return  ret;
    }
}
