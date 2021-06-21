package com.znlh.framework.statemachine.impl;

import com.znlh.framework.statemachine.Action;
import com.znlh.framework.statemachine.ActionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Objects;

public class SpringBeanActionFactory implements ActionFactory , ApplicationContextAware,InitializingBean {
    ApplicationContext applicationContext;


    @Override
    public Action getAction(String name) {
        if (Objects.nonNull(applicationContext)){
            return (Action) applicationContext.getBean(name);
        }
        return null;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
