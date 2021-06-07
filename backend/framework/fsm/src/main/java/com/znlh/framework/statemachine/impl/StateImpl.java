package com.znlh.framework.statemachine.impl;

import com.znlh.framework.statemachine.State;
import com.znlh.framework.statemachine.Transition;
import com.znlh.framework.statemachine.TransitionType;

import java.util.Collection;
import java.util.List;

public class StateImpl<S, E, C> implements State<S, E, C> {
    protected final S stateId;
    protected EventTransitions eventTransitions = new EventTransitions();


    public  StateImpl(S stateId){ this.stateId = stateId;}

    @Override
    public S getId() {
        return stateId;
    }

    @Override
    public Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType transitionType) {
        Transition<S, E, C> newTransition = new TransitionImpl<>();
        newTransition.setSource(this);
        newTransition.setTarget(target);
        newTransition.setEvent(event);
        newTransition.setType(transitionType);
        eventTransitions.put(event,newTransition);

        return newTransition;
    }

    @Override
    public List<Transition<S, E, C>> getEventTransitions(E event) {
        return eventTransitions.get(event);
    }

    @Override
    public Collection<Transition<S, E, C>> getAllTransitions() {
        return eventTransitions.allTransitions();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof State){
            State other = (State) o;
            if (this.stateId.equals(other.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return stateId.toString();
    }
}
