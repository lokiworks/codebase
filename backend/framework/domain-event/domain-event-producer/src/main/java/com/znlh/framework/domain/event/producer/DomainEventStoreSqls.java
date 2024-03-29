package com.znlh.framework.domain.event.producer;


public class DomainEventStoreSqls {

    private static final String INSERT_DOMAIN_EVENT_FIELDS = "event_id, event_type, entity_id, entity_data, entity_version";

    private static final String INSERT_DOMAIN_EVENT_SQL = "INSERT INTO ${TABLE_PREFIX}publish_event ("
            + INSERT_DOMAIN_EVENT_FIELDS + ") VALUES (?, ?, ?, ?, ?)";

    private static final String QUERY_DOMAIN_EVENT_SQL_BY_STATUS = "SELECT " + INSERT_DOMAIN_EVENT_FIELDS + " FROM ${TABLE_PREFIX}publish_event WHERE status=? AND del_flag=0 ORDER BY create_time ASC LIMIT ? ";

    private static final String UPDATE_DOMAIN_EVENT_STATUS_SQL = "UPDATE ${TABLE_PREFIX}publish_event SET `status` = ? WHERE event_id=? AND `status`= ?";

    private static final String UPDATE_DOMAIN_EVENT_RETRY_COUNT_SQL = "UPDATE ${TABLE_PREFIX}publish_event SET retry_count = retry_count+1 WHERE event_id=?  AND `retry_count`<=? ";

    private static final String RESET_DOMAIN_EVENT_PROCESS_STATUS_SQL = "UPDATE ${TABLE_PREFIX}publish_event SET  `status` = 'NEW' WHERE `status`='PROCESSING' AND update_time < ? AND `retry_count`<=? ";
    private static final String RESET_DOMAIN_EVENT_FAILED_STATUS_SQL = "UPDATE ${TABLE_PREFIX}publish_event SET  `status` = 'FAILED' WHERE `retry_count`> ? ";


    private static final String TABLE_PREFIX_REGEX = "\\$\\{TABLE_PREFIX}";

    private String tablePrefix;
    private String insertDomainEventSql;
    private String updateDomainEventStatusSql;
    private String queryDomainEventByStatusSql;
    private String updateDomainEventRetryCntSql;

    public String getResetDomainEventProcessStatusSql() {
        return resetDomainEventProcessStatusSql;
    }

    private String resetDomainEventProcessStatusSql;
    private String resetDomainEventFailedStatusSql;


    public DomainEventStoreSqls(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        init();
    }
    public String getResetDomainEventFailedStatusSql() {
        return resetDomainEventFailedStatusSql;
    }

    public static String getInsertDomainEventFields() {
        return INSERT_DOMAIN_EVENT_FIELDS;
    }
    public String getUpdateDomainEventRetryCntSql() {
        return updateDomainEventRetryCntSql;
    }
    public String getUpdateDomainEventStatusSql() {
        return updateDomainEventStatusSql;
    }
    public String getQueryDomainEventByStatusSql() {
        return queryDomainEventByStatusSql;
    }

    private void init() {
        insertDomainEventSql = INSERT_DOMAIN_EVENT_SQL.replaceAll(TABLE_PREFIX_REGEX, tablePrefix);
        queryDomainEventByStatusSql = QUERY_DOMAIN_EVENT_SQL_BY_STATUS.replaceAll(TABLE_PREFIX_REGEX, tablePrefix);
        updateDomainEventStatusSql = UPDATE_DOMAIN_EVENT_STATUS_SQL.replaceAll(TABLE_PREFIX_REGEX, tablePrefix);
        updateDomainEventRetryCntSql = UPDATE_DOMAIN_EVENT_RETRY_COUNT_SQL.replaceAll(TABLE_PREFIX_REGEX, tablePrefix);
        resetDomainEventProcessStatusSql = RESET_DOMAIN_EVENT_PROCESS_STATUS_SQL.replaceAll(TABLE_PREFIX_REGEX, tablePrefix);
        resetDomainEventFailedStatusSql = RESET_DOMAIN_EVENT_FAILED_STATUS_SQL.replaceAll(TABLE_PREFIX_REGEX, tablePrefix);
    }

    public String getInsertDomainEventSql() {
        return insertDomainEventSql;
    }
}
