package com.loki.dddplus.annotation;

import com.loki.dddplus.IDomainStep;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Service
public @interface Step {
    @AliasFor(annotation = Service.class, attribute = "value") String value() default "";
    String name();
    String[] tags() default {};
    Class<? extends IDomainStep>[] dependOn() default {};
}
