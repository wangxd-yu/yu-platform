-- 【日志表】登录日志表
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login`
(
    `id`          bigint(20) NOT NULL COMMENT 'ID',
    `username`    varchar(32) DEFAULT NULL COMMENT '用户名',
    `ip`          varchar(32) DEFAULT NULL COMMENT 'ip',
    `location`    varchar(32) DEFAULT NULL COMMENT 'ip归属地',
    `browser`     varchar(32) DEFAULT NULL COMMENT '浏览器',
    `os`          varchar(64) DEFAULT NULL COMMENT '操作系统',
    `dept_no`     varchar(32) DEFAULT NULL COMMENT '部门no',
    `tenant_id`   int         DEFAULT NULL COMMENT '租户ID',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB;