package com.znlh.framework.statemachine;

public interface Transition<S, E, C> {
    State<S, E, C> getSource();

    void setSource(State<S, E, C> state);

    E getEvent();

    void setEvent(E event);

    void setType(TransitionType type);


    State<S, E, C> getTarget();

    void setTarget(State<S, E, C> state);

    Condition<C> getCondition();

    void setCondition(Condition<C> condition);

    Action<S,E, C> getAction();

    void setAction(Action<S, E, C> action);

    State<S, E, C> transit(C ctx);

    void verify();



}
