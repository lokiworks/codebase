package com.znlh.framework.statemachine;

public interface StateMachineParser {
    StateMachine parse(String machineId, String json);
}
