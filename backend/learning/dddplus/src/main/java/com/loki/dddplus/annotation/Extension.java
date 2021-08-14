package com.loki.dddplus.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface Extension {
    @AliasFor(annotation = Component.class, attribute = "value") String value() default "";

    /**
     * 拓展点编号
     * @return
     */
    String code();

    /**
     * 拓展点名称.
     * @return
     */
    String name() default "";
}
