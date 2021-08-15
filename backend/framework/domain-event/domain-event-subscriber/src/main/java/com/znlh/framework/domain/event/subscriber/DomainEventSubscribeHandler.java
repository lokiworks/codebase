package com.znlh.framework.domain.event.subscriber;


import com.znlh.framework.domain.event.api.DomainEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
@Data
@Slf4j
public class DomainEventSubscribeHandler {
    private Object bean;
    private Method method;

    public void invoke(DomainEvent event){
        try {
            method.invoke(bean,event);
        } catch (IllegalAccessException|InvocationTargetException e) {
            log.error("invoke failed, eventId:{}",event.eventId(), e);
        }
    }

}
