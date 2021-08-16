
# xxx 表示对应领域的英文缩写
CREATE TABLE `xxx_publish_event` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `event_id` varchar(64) NOT NULL DEFAULT '',
                                     `event_type` varchar(64) NOT NULL DEFAULT '',
                                     `entity_id` varchar(64) NOT NULL DEFAULT '',
                                     `entity_data` longtext NOT NULL,
                                     `entity_version` varchar(64) NOT NULL DEFAULT '',
                                     `status` varchar(64) NOT NULL DEFAULT 'NEW' COMMENT 'NEW PROCESSING FAILED SUCCESS',
                                     `retry_count` int(255) unsigned NOT NULL DEFAULT '0',
                                     `del_flag` int(10) unsigned NOT NULL DEFAULT '0',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;