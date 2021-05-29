package com.znlh.framework.domain.event.producer;

import com.znlh.framework.domain.event.api.DomainEvent;
import com.znlh.framework.domain.event.api.DomainEventStatus;

import java.util.List;


interface DomainEventStore {
    int save(DomainEvent event);
    List<DomainEvent> batchPublish(int size);
    int markStatus(String eventId, DomainEventStatus dstStatus, DomainEventStatus srcStatus);
    int incRetryCnt(String eventId, Integer maxCnt);
    void resetProcessTimeout(Integer timeout,Integer maxCnt);
    void resetFailed(Integer maxCnt);
}
