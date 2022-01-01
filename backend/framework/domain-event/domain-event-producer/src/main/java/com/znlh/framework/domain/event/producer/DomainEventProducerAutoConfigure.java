package com.znlh.framework.domain.event.producer;

import com.znlh.framework.domain.event.api.DomainEventPublisher;
import com.znlh.framework.domain.event.api.DomainEventStoreProxyHook;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

import static com.znlh.framework.domain.event.api.DomainEventConstants.DOMAIN_EVENT_PREFIX;


@Configuration
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class})
@ConditionalOnProperty(prefix = DOMAIN_EVENT_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({DomainEventProducerProperties.class})
public class DomainEventProducerAutoConfigure {
    @Bean
    @ConditionalOnMissingBean(DomainEventStore.class)
    public DomainEventStore domainEventStore(DataSource dataSource, DomainEventProducerProperties producerProperties){
        DomainEventDBStore dbStore = new DomainEventDBStore(dataSource);
        dbStore.setTablePrefix(producerProperties.getTablePrefix() + "_");
        return dbStore;
    }
    @Bean
    @ConditionalOnMissingBean(DomainEventPublisher.class)
    public DomainEventPublisher domainEventPublisher(DomainEventProducerBinding binding, DomainEventStore domainEventStore){
        DomainEventPublisherImpl impl = new DomainEventPublisherImpl(binding.producer(),domainEventStore);
        return impl;
    }

    @Bean
    @ConditionalOnMissingBean(DomainEventPublishScheduler.class)
    public DomainEventPublishScheduler domainEventPublishScheduler(DomainEventPublisher domainEventPublisher) {
        DomainEventPublishScheduler domainEventPublishScheduler = new DomainEventPublishScheduler(domainEventPublisher);
        return domainEventPublishScheduler;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnMissingBean(DomainEventDBStoreProxy.class)
    public DomainEventDBStoreProxy domainEventDBStoreProxy(DomainEventStore store, DomainEventPublisher publisher,
                                                           List<DomainEventStoreProxyHook> hooks) {
        DomainEventDBStoreProxy proxy = new DomainEventDBStoreProxy(store, publisher, hooks);
        return proxy;
    }
}
