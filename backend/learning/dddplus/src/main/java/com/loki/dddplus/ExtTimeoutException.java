package com.loki.dddplus;

public class ExtTimeoutException extends RuntimeException {
    private final int timeoutInMs;
    ExtTimeoutException(int timeoutInMs){
        this.timeoutInMs = timeoutInMs;
    }


    @Override
    public String getMessage() {
        return "timeout:" + timeoutInMs + "ms";
    }
}
