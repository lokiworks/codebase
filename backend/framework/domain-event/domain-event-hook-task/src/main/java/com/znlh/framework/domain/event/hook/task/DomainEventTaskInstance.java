package com.znlh.framework.domain.event.hook.task;

public class DomainEventTaskInstance {
    private Long id;
    private String eventType;
    private String bean;
    private Integer processStatus;
    private Integer processCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Integer getProcessCount() {
        return processCount;
    }

    public void setProcessCount(Integer processCount) {
        this.processCount = processCount;
    }

    @Override
    public String toString() {
        return "DomainEventTaskInstance{" +
                "id=" + id +
                ", eventType='" + eventType + '\'' +
                ", bean='" + bean + '\'' +
                ", processStatus=" + processStatus +
                ", processCount=" + processCount +
                '}';
    }
}
