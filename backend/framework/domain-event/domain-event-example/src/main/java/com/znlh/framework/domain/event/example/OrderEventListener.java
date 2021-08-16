package com.znlh.framework.domain.event.example;

import com.alibaba.fastjson.JSON;
import com.znlh.framework.domain.event.subscriber.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {
    @EventHandler(target = ConsumerBinding.DOMAIN_EVENT_CONSUMER, eventType = OrderCreateEvent.EVENT_TYPE)
    public void handleCreateEvent(@Payload OrderCreateEvent event){
        log.info(JSON.toJSONString(event));

    }
}
