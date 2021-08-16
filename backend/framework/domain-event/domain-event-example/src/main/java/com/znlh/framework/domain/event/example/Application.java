package com.znlh.framework.domain.event.example;

import com.znlh.framework.domain.event.producer.DomainEventProducerBinding;
import com.znlh.framework.domain.event.subscriber.EventHandlerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableBinding({DomainEventProducerBinding.class, ConsumerBinding.class})
@Import(EventHandlerConfiguration.class)
public class Application {
    public static void main(String[] args) {
     SpringApplication.run(Application.class, args);
    }
}
