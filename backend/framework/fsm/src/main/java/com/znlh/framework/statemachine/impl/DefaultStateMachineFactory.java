package com.znlh.framework.statemachine.impl;

import com.znlh.framework.statemachine.Configuration;
import com.znlh.framework.statemachine.ConfigurationChangeEvent;
import com.znlh.framework.statemachine.StateMachine;
import com.znlh.framework.statemachine.StateMachineFactory;
import com.znlh.framework.statemachine.parser.StateMachineParser;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultStateMachineFactory implements StateMachineFactory {
    private Map<String, StateMachine> stateMachineMap = new ConcurrentHashMap<>();

    private Configuration configuration;

    private StateMachineParser stateMachineParser;


    private <S, E, C> void register(StateMachine<S, E, C> stateMachine) {
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
            String json = configuration.getConfig(machineId);
            if (Objects.isNull(json) || json.isEmpty()){
                // print logging
                return null;
            }
            configuration.addConfigListener(machineId,this::onChangeEvent);
            StateMachine result = stateMachineParser.parse(machineId, json);
            register(result);
            return result;

        }
        return stateMachine;
    }

    public  void onChangeEvent(ConfigurationChangeEvent event){
        StateMachine result = stateMachineParser.parse(event.getDataId(), event.getValue());
        register(result);
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setStateMachineParser(StateMachineParser stateMachineParser) {
        this.stateMachineParser = stateMachineParser;
    }
}
