package com.loki.dddplus.annotation;

import com.loki.dddplus.IDomainExtension;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface Policy {
    Class<? extends IDomainExtension> extClazz();
}
