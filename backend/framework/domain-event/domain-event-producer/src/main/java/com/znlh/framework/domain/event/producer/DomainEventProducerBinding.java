package com.znlh.framework.domain.event.producer;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DomainEventProducerBinding {
    String DOMAIN_EVENT_PRODUCER = "producer";
    @Output(DOMAIN_EVENT_PRODUCER)
    MessageChannel producer();
}
