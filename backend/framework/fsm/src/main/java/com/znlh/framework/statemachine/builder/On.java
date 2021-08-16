package com.znlh.framework.statemachine.builder;

import com.znlh.framework.statemachine.Condition;

public interface On<S, E, C> extends When<S, E, C> {
    /**
     * Add condition for the transition
     * @param condition transition condition
     * @return when clause builder
     */
    When<S, E, C> when(Condition<C> condition);
}
