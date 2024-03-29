package com.znlh.framework.domain.event.subscriber;



import com.znlh.framework.domain.event.api.DomainEventRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class DomainEventSubscribeAutoConfigure {
    @Bean
    @ConditionalOnMissingBean(DomainEventHandlingProcessor.class)
    public DomainEventHandlingProcessor domainEventHandlingProcessor(){
        DomainEventHandlingProcessor processor = new DomainEventHandlingProcessor();
        return processor;
    }
    @Bean
    @ConditionalOnMissingBean(DomainEventSubscribeCompensateScheduler.class)
    public DomainEventSubscribeCompensateScheduler domainEventSubscribeCompensateScheduler(@Autowired(required = false) List<DomainEventRetriever> retrievers){
        DomainEventSubscribeCompensateScheduler domainEventSubscribeCompensateScheduler = new DomainEventSubscribeCompensateScheduler(retrievers);
        return domainEventSubscribeCompensateScheduler;
    }
}
