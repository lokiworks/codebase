package com.znlh.framework.statemachine.builder;

import com.znlh.framework.statemachine.Action;

public interface When <S, E,C>{
    /**
     * Define action to be performed during transition
     * @param action performed action
     */
    void perform(Action<S, E, C> action);
}
