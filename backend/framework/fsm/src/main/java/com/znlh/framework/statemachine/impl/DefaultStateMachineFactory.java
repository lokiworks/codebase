package com.znlh.framework.statemachine.impl;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.znlh.framework.statemachine.StateMachine;
import com.znlh.framework.statemachine.StateMachineFactory;
import com.znlh.framework.statemachine.parser.StateMachineParser;
import com.znlh.framework.statemachine.parser.StateMachineParserImpl;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultStateMachineFactory implements StateMachineFactory {
    private Map<String, StateMachine> stateMachineMap = new ConcurrentHashMap<>();
    private String address;
    private String namespace;
    private String appGroup;
    private Integer timeout;

    StateMachineParser stateMachineParser = new StateMachineParserImpl();


    private  <S, E, C> void register(StateMachine<S, E, C> stateMachine) {
        String machineId = stateMachine.getMachineId();
        if (Objects.nonNull(stateMachineMap.get(machineId))) {
            throw new RuntimeException("The state machine with id [" + machineId + "] is already built, no need to build again");
        }
        stateMachineMap.put(stateMachine.getMachineId(), stateMachine);
    }

    @Override
    public <S, E, C> StateMachine<S, E, C> create(String machineId) {
        StateMachine stateMachine = stateMachineMap.get(machineId);
        if (Objects.isNull(stateMachine)) {
            //  从配置中心加载配置
            Properties properties = new Properties();
            properties.setProperty(NacosProperties.NAMESPACE, namespace);
            properties.setProperty(NacosProperties.SERVER_ADDR, address);
            ConfigService configService = null;
            try {
                configService = NacosFactory.createConfigService(properties);
                String json = configService.getConfig(machineId, appGroup, timeout);
                StateMachine result = stateMachineParser.parser(machineId, json);
                register(result);
                return result;
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }

        }
        return stateMachine;
    }
}
