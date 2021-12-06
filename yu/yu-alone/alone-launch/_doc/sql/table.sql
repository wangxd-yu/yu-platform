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
) ENGINE = INNODB COMMENT '登录接口日志';

-- 【日志表】端点日志表
DROP TABLE IF EXISTS `log_endpoint`;
CREATE TABLE `log_endpoint`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20) unsigned NOT NULL COMMENT '用户id',
    `username`    varchar(32)   NOT NULL COMMENT '用户名',
    `method`      varchar(20)   NOT NULL COMMENT '请求方法',
    `handler`     varchar(128)  NOT NULL COMMENT '处理器',
    `pattern`     varchar(128)  NOT NULL COMMENT '端点路径',
    `url`         varchar(1000) NOT NULL COMMENT '提交url',
    `request`     JSON     DEFAULT NULL COMMENT '请求数据',
    `response`    JSON     DEFAULT NULL COMMENT '响应数据',
    `http_status` smallint      NOT NULL COMMENT '响应状态',
    `ip`          varchar(16)   NOT NULL COMMENT 'ip地址',
    `time`        smallint DEFAULT null COMMENT '耗时',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int      DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT '端点日志表';