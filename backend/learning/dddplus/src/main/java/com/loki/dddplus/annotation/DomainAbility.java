package com.loki.dddplus.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface DomainAbility {
    @AliasFor(annotation = Component.class, attribute = "value") String value() default "";
    String domain();
    String name();
    String[] tags() default {};
}
