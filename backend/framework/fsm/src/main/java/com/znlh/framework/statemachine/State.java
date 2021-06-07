package com.znlh.framework.statemachine;

import java.util.Collection;
import java.util.List;

public interface State<S, E, C> {
    S getId();

    /**
     * Add transition to the state
     * @param event the event of the Transition
     * @param target the target of the transition
     * @param transitionType
     * @return
     */
    Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType transitionType);

    List<Transition<S, E, C>> getEventTransitions(E event);

    Collection<Transition<S, E, C>> getAllTransitions();

}
