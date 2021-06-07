package com.znlh.framework.statemachine.builder;

import com.znlh.framework.statemachine.*;
import com.znlh.framework.statemachine.impl.StateImpl;

import java.util.Map;

public class TransitionBuilderImpl <S, E, C> implements ExternalTransitionBuilder<S, E, C>, InternalTransitionBuilder<S, E, C>, From<S, E, C>, On<S, E, C>, To<S, E, C> {

    final Map<S, State<S, E, C>> stateMap ;
    private State<S, E, C> source;
    private State<S, E, C> target;
    private Transition<S, E, C> transition;
    private TransitionType transitionType;

    public TransitionBuilderImpl(Map<S, State<S, E, C>> stateMap, TransitionType transitionType){
        this.stateMap = stateMap;
        this.transitionType = transitionType;
    }

    @Override
    public From<S, E, C> from(S stateId) {
       this.source =  stateMap.computeIfAbsent(stateId, key-> new StateImpl<>(key) );
        return this;
    }

    @Override
    public To<S, E, C> to(S stateId) {
        this.target = stateMap.computeIfAbsent(stateId, key-> new StateImpl<>(key));
        return this;
    }

    @Override
    public To<S, E, C> within(S stateId) {
        this.source = this.target = stateMap.computeIfAbsent(stateId, key-> new StateImpl<>(key));
        return this;
    }

    @Override
    public When<S, E, C> when(Condition<C> condition) {
        transition.setCondition(condition);
        return this;
    }

    @Override
    public On<S, E, C> on(E event) {
        transition = source.addTransition(event, target, transitionType);
        return this;
    }

    @Override
    public void perform(Action<S, E, C> action) {
        transition.setAction(action);
    }
}

