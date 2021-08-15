package com.loki.dddplus.annotation;

public @interface Specification {
    String value();
    String[] tags() default {};
}
