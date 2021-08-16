package com.znlh.framework.statemachine.impl;

import com.znlh.framework.statemachine.State;
import com.znlh.framework.statemachine.StateMachine;
import com.znlh.framework.statemachine.Transition;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StateMachineImpl<S, E, C> implements StateMachine<S, E, C> {
    public String getMachineId() {
        return machineId;
    }

    @Override
    public Collection<State<S, E, C>> getStates() {
        return stateMap.values();
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    private String machineId;

    private final Map<S, State<S, E, C>> stateMap;

    private boolean ready;


    public StateMachineImpl(Map<S, State<S, E, C>> stateMap) {
        this.stateMap = stateMap;
    }

    @Override
    public S fireEvent(S sourceStateId, E event, C ctx) {
        if (!ready) {
            throw new RuntimeException("state machine is not build yet, can not work");
        }
        Transition<S, E, C> transition = routeTransition(sourceStateId, event, ctx);
        if (transition == null){
            return sourceStateId;
        }

        return transition.transit(ctx).getId();
    }

    private Transition<S, E, C> routeTransition(S sourceStateId, E event, C ctx) {
        State sourceState = stateMap.computeIfAbsent(sourceStateId, key -> new StateImpl(key));
        List<Transition<S, E, C>> transitions = sourceState.getEventTransitions(event);
        if (Objects.isNull(transitions) || transitions.isEmpty()){
            return null;
        }
        Transition<S, E, C> result = null;
        for (Transition<S, E, C>  transition: transitions){
            if (Objects.isNull(transition.getCondition())){
                result = transition;
            }else if (transition.getCondition().isSatisfied(ctx)){
                result = transition;
            }
        }
        return  result;
    }
}
