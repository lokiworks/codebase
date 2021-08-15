package com.znlh.framework.statemachine.impl;

import com.znlh.framework.statemachine.Transition;

import java.util.*;
import java.util.stream.Collectors;

public class EventTransitions <S, E, C>{
    private Map<E, List<Transition<S, E, C>>> eventTransitions = new HashMap<>();

    public void put(E event, Transition<S, E, C> transition){
        List<Transition<S, E, C>> existingTransitions =  eventTransitions.get(event);
        if (Objects.nonNull(existingTransitions) && !existingTransitions.isEmpty()){
            verify(existingTransitions, transition);
        }

        eventTransitions.computeIfAbsent(event, k -> new ArrayList<>()).add(transition);
    }

    public List<Transition<S, E, C>> get(E event){
        return eventTransitions.get(event);
    }

    public List<Transition<S, E, C>> allTransitions(){
       return eventTransitions.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    private void verify(List<Transition<S, E, C>> existingTransitions
    , Transition<S, E, C> newTransition ){
        for (Transition transition: existingTransitions){
            if (transition.equals(newTransition)){
                throw new RuntimeException(transition+" already Exist, you can not add another one");
            }
        }
    }




}
