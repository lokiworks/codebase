package com.znlh.framework.statemachine.builder;

public interface From <S, E, C>{
    To<S, E, C> to(S stateId);
}
