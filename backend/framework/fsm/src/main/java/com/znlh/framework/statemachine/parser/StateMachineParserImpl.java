package com.znlh.framework.statemachine.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.znlh.framework.statemachine.StateMachine;
import com.znlh.framework.statemachine.builder.StateMachineBuilder;
import com.znlh.framework.statemachine.builder.StateMachineBuilderFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StateMachineParserImpl implements StateMachineParser{
    ExpressionParser parser = new SpelExpressionParser();

    @Override
    public StateMachine parser(String machineId,String json) {

        List<Map<String, Object>> nodes =    JSON.parseObject(json, List.class);
        StateMachineBuilder stateMachineBuilder =  StateMachineBuilderFactory.create();
        for (Map<String, Object> node: nodes){
            String condition = (String) node.get("condition");
            if (Objects.nonNull(condition) && !condition.isEmpty()){
                stateMachineBuilder.externalTransition()
                        .from(node.get("sourceState"))
                        .to(node.get("targetState"))
                        .on(node.get("eventType")).when(
                                context -> {
                                    Expression exp = parser.parseExpression(condition);
                                    return exp.getValue(context,Boolean.class);
                                }
                );
            }else {
                stateMachineBuilder.externalTransition()
                        .from(node.get("sourceState"))
                        .to(node.get("targetState"))
                        .on(node.get("eventType"));
            }

        }
        return stateMachineBuilder.build(machineId);
    }
}
