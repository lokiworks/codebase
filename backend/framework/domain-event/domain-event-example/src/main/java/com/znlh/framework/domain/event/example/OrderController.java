package com.znlh.framework.domain.event.example;

import com.znlh.framework.domain.event.api.DefaultIdGenerator;
import com.znlh.framework.domain.event.api.DomainEvent;
import com.znlh.framework.domain.event.api.DomainEventStoreProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    DomainEventStoreProxy storeProxy;

    @GetMapping("create")
    public void create(){
        OrderDO order = new OrderDO();
        order.setOrderId("ID1001");
        order.setOrderName("test");
        DomainEvent event = OrderCreateEvent.builder().entity(order).eventId(DefaultIdGenerator.genId()).entityVersion("2.0").build();
        storeProxy.save(event);
    }
}
