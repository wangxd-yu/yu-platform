-- --------------------- 数据源模块 --------------------- --
-- 数据源表
DROP TABLE IF EXISTS `tb_datasource`;
CREATE TABLE `tb_datasource`
(
    `id`          bigint(20) NOT NULL COMMENT 'ID',
    `group_id`    bigint(20) NOT NULL COMMENT '组id',
    `tenant_id`   bigint(20) NOT NULL COMMENT '租户id',
    `url`         varchar(255) DEFAULT NULL COMMENT '连接信息',
    `username`    varchar(32)  DEFAULT NULL COMMENT '用户名',
    `password`    varchar(64)  DEFAULT NULL COMMENT '密码',
    `type`        varchar(32)  DEFAULT NULL COMMENT '数据库类型',
    `is_enabled`  tinyint unsigned DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;