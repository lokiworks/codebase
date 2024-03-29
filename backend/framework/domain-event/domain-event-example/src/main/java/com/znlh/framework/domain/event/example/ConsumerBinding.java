package com.znlh.framework.domain.event.example;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


public interface ConsumerBinding {
    // TODO
    String DOMAIN_EVENT_CONSUMER = "consumer";
    @Input(DOMAIN_EVENT_CONSUMER)
    SubscribableChannel consumer();
}
