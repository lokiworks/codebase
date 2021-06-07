package com.znlh.framework.statemachine.impl;

import com.znlh.framework.statemachine.*;

import java.util.Objects;

public class TransitionImpl<S, E, C> implements Transition<S, E, C> {

    private State<S, E, C> source;
    private State<S, E, C> target;

    private E event;

    private Condition<C> condition;

    private Action<S, E, C> action;

    private TransitionType type = TransitionType.EXTERNAL;


    @Override
    public State<S, E, C> getSource() {
        return this.source;
    }

    @Override
    public void setSource(State<S, E, C> state) {
        this.source = state;
    }

    @Override
    public E getEvent() {
        return this.event;
    }

    @Override
    public void setEvent(E event) {
        this.event = event;
    }

    @Override
    public void setType(TransitionType type) {
        this.type = type;
    }

    @Override
    public State<S, E, C> getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(State<S, E, C> state) {
        this.target = target;
    }

    @Override
    public Condition<C> getCondition() {
        return this.condition;
    }

    @Override
    public void setCondition(Condition<C> condition) {
        this.condition = condition;
    }

    @Override
    public Action<S, E, C> getAction() {
        return this.action;
    }

    @Override
    public void setAction(Action<S, E, C> action) {
        this.action = action;
    }

    @Override
    public State<S, E, C> transit(C ctx) {
        this.verify();
        if (Objects.isNull(condition) || condition.isSatisfied(ctx)) {
            if (Objects.nonNull(action)) {
                action.execute(source.getId(), target.getId(), event, ctx);
            }
            return target;
        }
        return source;
    }

    @Override
    public void verify() {
        if (type == TransitionType.INTERNAL && source != target) {
            throw new RuntimeException(String.format("Internal transition source state '%s' " + "and target state '%s' must be same.", source, target));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Transition) {
            Transition other = (Transition) o;
            if (this.event.equals(other.getEvent()) &&
                    this.source.equals(other.getSource()) &&
                    this.target.equals(other.getTarget())) {
                return true;
            }
        }
        return false;
    }
}
