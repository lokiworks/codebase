package com.loki.dddplus.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface Pattern {
    String code();
    String name();
    String[] tags() default {};

    /**
     * 优先级， 越小表示优先级越高
     * @return
     */
    int priority() default  1024;
}
