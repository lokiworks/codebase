package com.znlh.framework.statemachine.builder;

public interface InternalTransitionBuilder<S, E, C> {
    To<S, E, C> within(S stateId);
}
