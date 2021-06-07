package com.znlh.framework.statemachine;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class StateMachineFactory {
    static Map<String, StateMachine> stateMachineMap = new ConcurrentHashMap<>();

    public static <S, E, C> void register(StateMachine<S, E, C> stateMachine){
        String machineId = stateMachine.getMachineId();
        if (Objects.nonNull(stateMachineMap.get(machineId))){
            throw new RuntimeException("The state machine with id [" + machineId + "] is already built, no need to build again");
        }
        stateMachineMap.put(stateMachine.getMachineId(), stateMachine);
    }

    public static <S, E, C> StateMachine<S, E, C> get(String machineId){
        StateMachine stateMachine = stateMachineMap.get(machineId);
        if (Objects.isNull(stateMachine)){
            throw new RuntimeException("There is no stateMachine instance for " + machineId + ", please build it first");
        }
        return stateMachine;
    }
}
