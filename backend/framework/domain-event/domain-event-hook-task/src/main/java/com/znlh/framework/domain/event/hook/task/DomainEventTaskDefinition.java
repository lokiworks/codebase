package com.znlh.framework.domain.event.hook.task;

public class DomainEventTaskDefinition {
    private Long id;
    private String eventType;
    private String bean;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DomainEventTaskDefinition{" +
                "id=" + id +
                ", eventType='" + eventType + '\'' +
                ", bean='" + bean + '\'' +
                '}';
    }
}
