package com.znlh.framework.domain.event.api;


public enum DomainEventStatus {
    NEW("NEW"),
    PROCESSING("PROCESSING"),
    FAILED("FAILED"),
    SUCCESS("SUCCESS");

    public String getStatus() {
        return status;
    }

    private String status;

    DomainEventStatus(String status){
        this.status = status;
    }
}
