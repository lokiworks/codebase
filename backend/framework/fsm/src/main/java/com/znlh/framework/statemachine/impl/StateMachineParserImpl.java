package com.znlh.framework.statemachine.impl;

import com.alibaba.fastjson.JSON;
import com.znlh.framework.statemachine.Action;
import com.znlh.framework.statemachine.ActionFactory;
import com.znlh.framework.statemachine.StateMachine;
import com.znlh.framework.statemachine.StateMachineParser;
import com.znlh.framework.statemachine.builder.StateMachineBuilder;
import com.znlh.framework.statemachine.builder.StateMachineBuilderFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StateMachineParserImpl implements StateMachineParser {
    ExpressionParser parser;
    ActionFactory actionFactory;


    @Override
    public StateMachine parse(String machineId, String json) {

        List<Map<String, Object>> nodes =    JSON.parseObject(json, List.class);
        StateMachineBuilder stateMachineBuilder =  StateMachineBuilderFactory.create();
        for (Map<String, Object> node: nodes){
            String condition = (String) node.get("condition");
            String actionName = (String) node.get("action");

            if (Objects.nonNull(condition) && !condition.isEmpty()){

                if (Objects.nonNull(actionName) && !actionName.isEmpty()){
                    Action action = (Action) actionFactory.getBean(actionName);
                    if (Objects.isNull(action)){
                        throw new RuntimeException("bean name [" + actionName + "] not found");
                    }

                    stateMachineBuilder.externalTransition()
                            .from(node.get("sourceState"))
                            .to(node.get("targetState"))
                            .on(node.get("eventType")).when(
                            context -> {
                                Expression exp = parser.parseExpression(condition);
                                return exp.getValue(context,Boolean.class);
                            }
                    ).perform(action);
                }else {

                    stateMachineBuilder.externalTransition()
                            .from(node.get("sourceState"))
                            .to(node.get("targetState"))
                            .on(node.get("eventType")).when(
                            context -> {
                                Expression exp = parser.parseExpression(condition);
                                return exp.getValue(context,Boolean.class);
                            }
                    );
                }

            }else {
                if (Objects.nonNull(actionName) && !actionName.isEmpty()){
                    Action action = (Action) actionFactory.getBean(actionName);
                    if (Objects.isNull(action)){
                        throw new RuntimeException("bean name [" + actionName + "] not found");
                    }
                    stateMachineBuilder.externalTransition()
                            .from(node.get("sourceState"))
                            .to(node.get("targetState"))
                            .on(node.get("eventType")).perform(action);
                }else {
                    stateMachineBuilder.externalTransition()
                            .from(node.get("sourceState"))
                            .to(node.get("targetState"))
                            .on(node.get("eventType"));
                }

            }

        }
        return stateMachineBuilder.build(machineId);
    }

    public void setActionFactory(ActionFactory actionFactory) {
        this.actionFactory = actionFactory;
    }

    public void setParser(ExpressionParser parser) {
        this.parser = parser;
    }
}
