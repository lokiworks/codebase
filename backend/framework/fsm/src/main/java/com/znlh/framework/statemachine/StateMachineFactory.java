package com.znlh.framework.statemachine;

public interface StateMachineFactory {
      <S, E, C> StateMachine<S, E, C> create(String machineId);
}
