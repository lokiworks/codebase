package com.znlh.framework.domain.event.producer;


import com.znlh.framework.domain.event.api.DomainEventConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = DomainEventConstants.DOMAIN_EVENT_PREFIX)
@Data
public class DomainEventProducerProperties {
    private boolean enabled = true;
    private String tablePrefix;
}
