package com.znlh.framework.statemachine.impl;

import com.znlh.framework.statemachine.ActionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

public class SpringBeanActionFactory implements ActionFactory , InitializingBean {
    ApplicationContext applicationContext;


    @Override
    public Object getBean(String name) {
        if (Objects.nonNull(applicationContext)){
            return applicationContext.getBean(name);
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
