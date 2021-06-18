package com.znlh.framework.statemachine;

import com.znlh.framework.statemachine.StateMachine;

public interface StateMachineParser {
    StateMachine parse(String machineId, String json);
}
