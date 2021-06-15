package com.znlh.framework.statemachine;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.znlh.framework.statemachine.parser.StateMachineParser;
import com.znlh.framework.statemachine.parser.StateMachineParserImpl;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class StateMachineFactory {
    static Map<String, StateMachine> stateMachineMap = new ConcurrentHashMap<>();
    // ???
    static volatile Object lock = new Object();

    static String address;
    static String namespace;
    static String appGroup;
    static Integer timeout;

    static StateMachineParser stateMachineParser = new StateMachineParserImpl();


    public static <S, E, C> void register(StateMachine<S, E, C> stateMachine){
        String machineId = stateMachine.getMachineId();
        if (Objects.nonNull(stateMachineMap.get(machineId))){
            throw new RuntimeException("The state machine with id [" + machineId + "] is already built, no need to build again");
        }
        stateMachineMap.put(stateMachine.getMachineId(), stateMachine);
    }

    public static <S, E, C> StateMachine<S, E, C> get(String machineId) throws NacosException {
        StateMachine stateMachine = stateMachineMap.get(machineId);
        if (Objects.isNull(stateMachine)){
            //  从配置中心加载配置
            synchronized (lock){
                Properties properties = new Properties();
                properties.setProperty(NacosProperties.NAMESPACE, namespace);
                properties.setProperty(NacosProperties.SERVER_ADDR, address);
                ConfigService configService = NacosFactory.createConfigService(properties);
                String json =    configService.getConfig(machineId, appGroup, timeout);
                StateMachine result  = stateMachineParser.parser(machineId, json);
                register(result);
                return result;
            }
        }
        return stateMachine;
    }
}
