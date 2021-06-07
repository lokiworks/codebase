package com.znlh.framework.statemachine.builder;

import com.znlh.framework.statemachine.StateMachine;

public interface StateMachineBuilder<S, E, C> {
    ExternalTransitionBuilder<S, E, C> externalTransition();

    InternalTransitionBuilder<S, E, C> internalTransition();

    StateMachine<S, E, C> build(String machineId);


}
