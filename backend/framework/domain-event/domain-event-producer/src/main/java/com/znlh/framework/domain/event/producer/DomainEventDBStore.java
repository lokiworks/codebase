package com.znlh.framework.domain.event.producer;

import com.alibaba.fastjson.JSON;
import com.znlh.framework.domain.event.api.DomainEvent;
import com.znlh.framework.domain.event.api.DomainEventStatus;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;


 class DomainEventDBStore extends  AbstractStore implements DomainEventStore {
    private static final  DomainEventToStatement DOMAIN_EVENT_TO_STATEMENT = new DomainEventToStatement();
    private static final ResultSetToDomainEvent RESULT_SET_TO_DOMAIN_EVENT = new ResultSetToDomainEvent();
    private  DomainEventStoreSqls eventStoreSqls;

    public DomainEventDBStore(DataSource dataSource){
        super.dataSource = dataSource;
    }

    @Override
    public int save(DomainEvent event) {
        return super.executeUpdate(eventStoreSqls.getInsertDomainEventSql(), DOMAIN_EVENT_TO_STATEMENT, event);

    }

    @Override
    public void setTablePrefix(String tablePrefix) {
        super.setTablePrefix(tablePrefix);
        this.eventStoreSqls = new DomainEventStoreSqls(tablePrefix);
    }


    @Override
    public List<DomainEvent> batchPublish(int size) {
        List<DomainEvent> result =   super.selectList(eventStoreSqls.getQueryDomainEventByStatusSql(),RESULT_SET_TO_DOMAIN_EVENT, DomainEventStatus.NEW.getStatus(), size);
        if (Objects.isNull(result)){
            return Collections.emptyList();
        }
        return result;
    }

    @Override
    public int markStatus(String eventId, DomainEventStatus dstStatus, DomainEventStatus srcStatus) {
        return  super.executeUpdate(eventStoreSqls.getUpdateDomainEventStatusSql(),  dstStatus.getStatus(), eventId,srcStatus.getStatus());
    }

    @Override
    public int incRetryCnt(String eventId, Integer maxCnt) {
         return super.executeUpdate(eventStoreSqls.getUpdateDomainEventRetryCntSql(),eventId, maxCnt);
    }

    @Override
    public void resetProcessTimeout(Integer timeout,Integer maxCnt) {
        Date beforeDate = new Date(new Date().getTime() + (timeout * 60000));
        super.executeUpdate(eventStoreSqls.getResetDomainEventProcessStatusSql(), beforeDate, maxCnt);
    }

    @Override
    public void resetFailed(Integer maxCnt) {
         super.executeUpdate(eventStoreSqls.getResetDomainEventFailedStatusSql(),maxCnt);
    }


    private static class DomainEventToStatement implements AbstractStore.ObjectToStatement<DomainEvent>{

        @Override
        public void toStatement(DomainEvent event, PreparedStatement stmt) throws SQLException {
            Objects.requireNonNull(event);
            Objects.requireNonNull(event.entity());
            stmt.setString(1, event.eventId());
            stmt.setString(2, event.eventType());
            stmt.setString(3, event.entityId());
            stmt.setString(4, JSON.toJSONString(event.entity()));
            stmt.setString(5, event.entityVersion());
        }
    }
    private static class ResultSetToDomainEvent implements AbstractStore.ResultSetToObject<DomainEvent>{

        @Override
        public DomainEvent toObject(ResultSet resultSet) throws SQLException {
            DomainEventImpl event = new DomainEventImpl();
            event.setEventId(resultSet.getString("event_id"));
            event.setEventType(resultSet.getString("event_type"));
            event.setEntityId(resultSet.getString("entity_id"));
            event.setEntity(JSON.parseObject(resultSet.getString("entity_data")));
            event.setEntityType(resultSet.getString("event_type"));
            event.setEntityVersion(resultSet.getString("entity_version"));
            return event;
        }
    }
}
