package com.znlh.framework.domain.event.api;

import java.util.UUID;


/**
 * 领域事件
 */
public interface DomainEvent<T> {
    /**
     * 事件ID
     * @return
     */
    String eventId();

    /**
     * 事件类型
     * @return
     */
    String eventType();

    /**
     * 领域实体
     * @return
     */
    T entity();

    String entityId();

    String entityType();

   default String entityVersion(){
       return "1.0";
   }



}
