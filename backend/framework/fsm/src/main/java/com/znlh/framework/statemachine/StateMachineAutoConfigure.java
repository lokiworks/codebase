package com.znlh.framework.statemachine;

import com.znlh.framework.statemachine.impl.DefaultStateMachineFactory;
import com.znlh.framework.statemachine.impl.NacosConfiguration;
import com.znlh.framework.statemachine.parser.StateMachineParser;
import com.znlh.framework.statemachine.parser.StateMachineParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(prefix = StateMachineConstants.STATE_MACHINE_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({StateMachineProperties.class})
public class StateMachineAutoConfigure {
    @Bean
    @ConditionalOnMissingBean(StateMachineFactory.class)
    public StateMachineFactory stateMachineFactory(com.znlh.framework.statemachine.Configuration configuration,StateMachineParser stateMachineParser){

        DefaultStateMachineFactory stateMachineFactory = new DefaultStateMachineFactory();
        stateMachineFactory.setStateMachineParser(stateMachineParser);
        stateMachineFactory.setConfiguration(configuration);
        return stateMachineFactory;
    }
    @Bean
    @ConditionalOnMissingBean(StateMachineParser.class)
    public StateMachineParser stateMachineParser(){
        return new StateMachineParserImpl();
    }

    @Bean
    @ConditionalOnMissingBean(com.znlh.framework.statemachine.Configuration.class)
    public com.znlh.framework.statemachine.Configuration configuration(StateMachineProperties properties){
        NacosConfiguration configuration = new NacosConfiguration();

        configuration.setTimeout(properties.getTimeout());
        configuration.setGroup(properties.getGroup());
        configuration.setAddress(properties.getAddress());
        configuration.setNamespaceId(properties.getNamespaceId());
        configuration.init();
        return configuration;
    }

}
