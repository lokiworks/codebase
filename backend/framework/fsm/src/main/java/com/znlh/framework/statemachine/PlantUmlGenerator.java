package com.znlh.framework.statemachine;

public class PlantUmlGenerator {
   public static final char LF = '\n';

    public static String generate(StateMachine<?,?,?> stateMachine){
        StringBuilder sb = new StringBuilder();
        sb.append(onEntry(stateMachine));
        for (State state : stateMachine.getStates()) {
            sb.append(onEntry(state));
        }
        sb.append(onExit(stateMachine));
        return sb.toString();
    }

    public static String onEntry(StateMachine<?,?,?> stateMachine){
        return "@startuml" + LF;
    }

    public static String onExit(StateMachine<?, ?, ?> stateMachine) {
        return "@enduml";
    }

    public static String onEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        for(Transition transition: state.getAllTransitions()){
            sb.append(transition.getSource().getId())
                    .append(" --> ")
                    .append(transition.getTarget().getId())
                    .append(" : ")
                    .append(transition.getEvent())
                    .append(LF);
        }
        return sb.toString();
    }
}
