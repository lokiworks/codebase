package com.znlh.framework.domain.event.api;

import java.util.List;


public interface DomainEventRetriever {
    List<DomainEvent> batchRetrieve();
}
