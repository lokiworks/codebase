package com.znlh.framework.domain.event.example;

import com.znlh.framework.domain.event.api.DefaultIdGenerator;
import com.znlh.framework.domain.event.api.DomainEvent;
import com.znlh.framework.domain.event.api.DomainEventRetriever;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OrderCreateEventRetriever implements DomainEventRetriever {
    @Override
    public List<DomainEvent> batchRetrieve() {
        OrderDO order = new OrderDO();
        order.setOrderId("ID1001");
        order.setOrderName("test");
        DomainEvent event = OrderCreateEvent.builder().entity(order).eventId(DefaultIdGenerator.genId()).entityVersion("2.0").build();
        return Arrays.asList(event);
    }
}
