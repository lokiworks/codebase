package com.znlh.framework.statemachine;

public interface Condition<C> {
    boolean isSatisfied(C context);
}
