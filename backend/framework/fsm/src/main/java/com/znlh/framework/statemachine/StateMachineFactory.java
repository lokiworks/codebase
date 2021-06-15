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

public interface StateMachineFactory {
      <S, E, C> StateMachine<S, E, C> create(String machineId);
}
