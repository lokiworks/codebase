package com.znlh.framework.statemachine;

/**
 * StateContext
 * @param <S> the state
 * @param <E> the event
 * @param <C> the condition
 */
public interface StateContext<S, E, C> {
    Transition<S, E, C> getTransition();
    StateMachine<S, E, C> getStateMachine();

}
