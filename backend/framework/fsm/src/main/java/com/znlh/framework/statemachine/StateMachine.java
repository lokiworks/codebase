package com.znlh.framework.statemachine;

import java.util.Collection;

public interface StateMachine<S, E, C> {

    /**
     * send an event {@code E} to the state machine
     * @param sourceState the source state
     * @param event the event to send
     * @param ctx the user defined context
     * @return the target state
     */
    S fireEvent(S sourceState, E event, C ctx);

    String getMachineId();

    Collection< State<S, E, C> > getStates();


}
