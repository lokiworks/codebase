package com.znlh.framework.statemachine.builder;

public class StateMachineBuilderFactory {
    public static <S,E,C> StateMachineBuilder<S, E, C> create(){
        return new StateMachineBuilderImpl<>();
    }
}
