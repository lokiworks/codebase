package com.znlh.framework.domain.event.api;


/**
 * 领域对象
 */
public interface DomainEntity {
    Object getData();
    String version();
    String entityType();
    String entityId();
}
