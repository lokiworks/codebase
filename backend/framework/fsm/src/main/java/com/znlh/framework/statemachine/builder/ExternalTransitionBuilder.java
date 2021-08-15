package com.znlh.framework.statemachine.builder;

public interface ExternalTransitionBuilder <S, E,C>{
    From<S, E, C> from(S stateId);
}
