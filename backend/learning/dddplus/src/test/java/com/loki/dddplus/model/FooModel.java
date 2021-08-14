package com.loki.dddplus.model;

import com.loki.dddplus.IDomainModel;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FooModel implements IDomainModel {
    private String partnerCode;
    private boolean b2c;
    private boolean letFooThrowException;
    private boolean willSleepLong;
    private boolean willThrowRuntimeException;
    private boolean willThrowOOM;
    private boolean redecide;
    private boolean redecideDeadLoop;
    private boolean stepsRevised;
    private boolean willRollback;
    private boolean willRollbackInvalid;
    private boolean sleepExtTimeout;
    private int foo;
}
