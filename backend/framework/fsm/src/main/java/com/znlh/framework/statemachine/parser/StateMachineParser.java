package com.znlh.framework.statemachine.parser;

import com.znlh.framework.statemachine.StateMachine;

public interface StateMachineParser {
    StateMachine parse(String machineId, String json);
}
