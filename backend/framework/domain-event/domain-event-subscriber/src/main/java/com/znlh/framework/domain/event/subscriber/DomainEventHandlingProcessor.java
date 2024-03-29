package com.znlh.framework.domain.event.subscriber;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;



public class DomainEventHandlingProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (Objects.nonNull(methods)) {
            for (Method method : methods) {
                EventHandler event = AnnotationUtils.findAnnotation(method, EventHandler.class);
                if (Objects.nonNull(event)) {
                    DomainEventSubscribeHandler handler = new DomainEventSubscribeHandler();
                    handler.setBean(bean);
                    handler.setMethod(method);
                    DomainEventDispatcher.getInstance().register(event.eventType(),handler);
                }
            }
        }
        return bean;
    }
}

