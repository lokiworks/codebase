package com.znlh.framework;

import static org.junit.Assert.assertTrue;

import com.znlh.framework.statemachine.*;
import com.znlh.framework.statemachine.builder.On;
import com.znlh.framework.statemachine.builder.StateMachineBuilder;
import com.znlh.framework.statemachine.builder.StateMachineBuilderFactory;
import com.znlh.framework.statemachine.impl.DefaultStateMachineFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Unit test for simple App.
 */
public class StateMachineTest {
    enum PriceAdjustmentTaskStatusEnum {
        /**
         * 开始状态
         */
        None,
        /**
         * 待商家处理
         */
        Supplier_Processing,
        /**
         * 待控商小二处理
         */
        Supplier_Manager_Processing,

        /**
         * 待价格管控小儿处理
         */
        Price_Manager_Processing,

        /**
         * 退出
         */
        Closed

    }

    enum PriceAdjustmentTaskEventEnum {
        // 系统事件
        Create,
        Normal_Update,
        /**
         * 合理价变更
         */
        P0_Changed,
        /**
         * Page_Price_changed,
         */
        // 商家事件
        Supplier_Reject,
        Supplier_Agree,
        Supplier_Timeout,
        // 控商小二事件
        Apply_Over_P0_Sell,
        Agree_Over_P0_Sell,
        Reject_Over_P0_Sell,
    }

    class Context{
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    @Test
    public void simpleFlow() {
        StateMachineBuilder<PriceAdjustmentTaskStatusEnum,PriceAdjustmentTaskEventEnum,Object> builder = StateMachineBuilderFactory.create();
        Context context = new Context();
        context.setAge(60);
        context.setName("loki");

        builder.externalTransition().from(PriceAdjustmentTaskStatusEnum.None).to(PriceAdjustmentTaskStatusEnum.Supplier_Processing).on(PriceAdjustmentTaskEventEnum.Create).when(x->{
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression("age >=10");
            return exp.getValue(x, Boolean.class);});



        // 商家 -上升至-> 控商小二
        builder.externalTransition()
                .from(PriceAdjustmentTaskStatusEnum.Supplier_Processing)
                .to(PriceAdjustmentTaskStatusEnum.Supplier_Manager_Processing)
                .on(PriceAdjustmentTaskEventEnum.Supplier_Reject).when(x->{
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression("age >=10");
            return exp.getValue(x, Boolean.class);});

        builder.externalTransition()
                .from(PriceAdjustmentTaskStatusEnum.Supplier_Processing)
                .to(PriceAdjustmentTaskStatusEnum.Supplier_Manager_Processing)
                .on(PriceAdjustmentTaskEventEnum.Supplier_Timeout).when(x->{
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression("age >=20");
            return exp.getValue(x, Boolean.class);});
        // 申请申请高于P0售卖
        builder.externalTransition()
                .from(PriceAdjustmentTaskStatusEnum.Supplier_Manager_Processing)
                .to(PriceAdjustmentTaskStatusEnum.Price_Manager_Processing)
                .on(PriceAdjustmentTaskEventEnum.Apply_Over_P0_Sell)
                .when(x->{
                    ExpressionParser parser = new SpelExpressionParser();
                    Expression exp = parser.parseExpression("age >=40");
                    return exp.getValue(x, Boolean.class);})
        ;

        // 同意高于P0价售卖
        builder.externalTransition()
                .from(PriceAdjustmentTaskStatusEnum.Price_Manager_Processing)
                .to(PriceAdjustmentTaskStatusEnum.Closed)
                .on(PriceAdjustmentTaskEventEnum.Agree_Over_P0_Sell)
                .when(x->{
                    ExpressionParser parser = new SpelExpressionParser();
                    Expression exp = parser.parseExpression("age >=50");
                    Boolean ret = exp.getValue(x, Boolean.class);
                return  ret;})
        ;

        // 拒绝高于P0价售卖
        builder.externalTransition()
                .from(PriceAdjustmentTaskStatusEnum.Price_Manager_Processing)
                .to(PriceAdjustmentTaskStatusEnum.Supplier_Manager_Processing)
                .on(PriceAdjustmentTaskEventEnum.Reject_Over_P0_Sell)
                .when(x->{
                    ExpressionParser parser = new SpelExpressionParser();
                    Expression exp = parser.parseExpression("age >=60");
                    return exp.getValue(x, Boolean.class);})
        ;
        StateMachine<PriceAdjustmentTaskStatusEnum,PriceAdjustmentTaskEventEnum,Object> stateMachine = builder.build("AdjustPriceTask");


        PriceAdjustmentTaskStatusEnum target = stateMachine.fireEvent(PriceAdjustmentTaskStatusEnum.Price_Manager_Processing, PriceAdjustmentTaskEventEnum.Agree_Over_P0_Sell, context);
        Assert.assertEquals(target, PriceAdjustmentTaskStatusEnum.Closed);
        String plantUml = PlantUmlGenerator.generate(stateMachine);
        System.out.println(plantUml);


    }

    /**
     * [
     *     {
     *         "sourceState":"S1",
     *         "targetState":"S2",
     *         "eventType":"E1",
     *         "conditon":"",
     *         "action":""
     *     }
     * ]
     */
    @Test
    public void dsl(){
        StateMachineProperties properties = new StateMachineProperties();
        properties.setAddress("http://192.168.3.200:8848");
        properties.setGroup("ORDER_GROUP");
        properties.setNamespaceId("25d3bc09-677c-4b9c-9889-72b5c5e41520");

        StateMachineAutoConfigure autoConfigure = new StateMachineAutoConfigure();
        autoConfigure.configuration(properties);

        StateMachineFactory stateMachineFactory =  autoConfigure.stateMachineFactory(autoConfigure.configuration(properties),autoConfigure.stateMachineParser());
       StateMachine s = stateMachineFactory.create("simple");
       int i = 3;
    }
}
