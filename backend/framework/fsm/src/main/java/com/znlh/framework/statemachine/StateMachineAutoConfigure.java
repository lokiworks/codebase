package com.znlh.framework.statemachine;

import com.znlh.framework.statemachine.impl.DefaultStateMachineFactory;
import com.znlh.framework.statemachine.impl.NacosConfiguration;
import com.znlh.framework.statemachine.impl.SpringBeanActionFactory;
import com.znlh.framework.statemachine.impl.StateMachineParserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;


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
    public StateMachineParser stateMachineParser(ActionFactory actionFactory){
        StateMachineParserImpl parser =  new StateMachineParserImpl();
        parser.setActionFactory(actionFactory);
        parser.setParser(new SpelExpressionParser());
        return parser;
    }
    @Bean
    @ConditionalOnMissingBean(ActionFactory.class)
    public ActionFactory actionFactory(){
        return new SpringBeanActionFactory();
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
