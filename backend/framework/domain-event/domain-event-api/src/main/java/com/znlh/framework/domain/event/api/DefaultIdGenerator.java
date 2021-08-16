package com.znlh.framework.domain.event.api;

import java.util.UUID;

public class DefaultIdGenerator {
    public static final String genId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
