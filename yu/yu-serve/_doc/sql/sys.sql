-- --------------------- 系统管理模块 --------------------- --
-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`                   bigint(20) NOT NULL COMMENT 'ID',
    `username`             varchar(32)      DEFAULT NULL COMMENT '用户名',
    `password`             varchar(68)      DEFAULT NULL COMMENT '密码',
    `is_enabled`           tinyint unsigned DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `name`                 varchar(32)      DEFAULT NULL COMMENT '名称',
    `password_update_time` datetime         DEFAULT NULL COMMENT '密码修改时间',
    `dept_no`              varchar(32)      DEFAULT NULL COMMENT '部门no',
    `tenant_id`            int              DEFAULT NULL COMMENT '租户ID',
    `create_time`          datetime         DEFAULT NULL COMMENT '创建时间',
    `update_time`          datetime         DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20) NOT NULL COMMENT 'ID',
    `code`        varchar(16)  DEFAULT NULL COMMENT '编号',
    `name`        varchar(16)  DEFAULT NULL COMMENT '名称',
    `remark`      varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;

-- 【关系表】用户-角色
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB;

-- 菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          BIGINT ( 20 ) NOT NULL COMMENT 'ID',
    `pid`         BIGINT ( 20 ) NOT NULL COMMENT '上级菜单ID',
    `name`        VARCHAR(255) DEFAULT NULL COMMENT '菜单名称',
    `type`        enum ( 'FOLDER', 'MENU', 'PERMISSION' ) DEFAULT NULL COMMENT '类型',
    `path`        VARCHAR(255) DEFAULT NULL COMMENT '链接地址',
    `component`   VARCHAR(255) DEFAULT NULL COMMENT '组件',
    `permission`  VARCHAR(255) DEFAULT NULL COMMENT '权限编码',
    `sort`        INT          DEFAULT NULL COMMENT '排序',
    `icon`        VARCHAR(255) DEFAULT NULL COMMENT '图标',
    `is_frame`    TINYINT UNSIGNED DEFAULT NULL COMMENT '是否外链',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   INT          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB;

-- 【关系表】角色-菜单
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB;

-- 字典表-主表
CREATE TABLE `sys_dict`
(
    `id`          bigint(20) NOT NULL,
    `code`        varchar(16)  DEFAULT NULL COMMENT '编号',
    `name`        varchar(16)  DEFAULT NULL COMMENT '名称',
    `remark`      varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int          DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- 字典表-明细表
CREATE TABLE `sys_dict_detail`
(
    `id`          bigint(20) NOT NULL,
    `dict_id`     bigint(20) NOT NULL,
    `code`        varchar(16) DEFAULT NULL COMMENT '编号',
    `name`        varchar(16) DEFAULT NULL COMMENT '名称',
    `sort`        tinyint     DEFAULT NULL COMMENT '排序',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime    DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int         DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- 部门表
drop table if exists `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`          bigint      NOT NULL,
    `no`          varchar(32) NOT NULL COMMENT '上下级关系编码',
    `pno`         varchar(32) NOT NULL COMMENT '上级部门no',
    `code`        varchar(32)      DEFAULT NULL COMMENT '用户自定义编码',
    `sort`        tinyint          DEFAULT NULL COMMENT '排序',
    `type_id`     bigint           DEFAULT NULL COMMENT '类型id',
    `type_code`   varchar(16)      DEFAULT NULL COMMENT '类型编号',
    `name`        varchar(32)      DEFAULT NULL COMMENT '名称',
    `is_enabled`  tinyint unsigned DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `sub_count`   int              DEFAULT '0' COMMENT '子部门数目',
    `create_time` datetime         DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime         DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int              DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;

-- 部门类型表
drop table if exists `sys_dept_type`;
CREATE TABLE `sys_dept_type`
(
    `id`          bigint NOT NULL,
    `code`        varchar(16) DEFAULT NULL COMMENT '编号',
    `name`        varchar(32) DEFAULT NULL COMMENT '名称',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime    DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int         DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;

-- 部门类型规则表
drop table if exists `sys_dept_type_role`;
CREATE TABLE `sys_dept_type_role`
(
    `id`          bigint NOT NULL,
    `type_id`     bigint NOT NULL COMMENT '主类型id',
    `sub_type_id` bigint NOT NULL COMMENT '子类型id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int      DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB;

-- 端点表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB;

-- 【关系表】菜单-端点
DROP TABLE IF EXISTS `sys_endpoint`;
CREATE TABLE `sys_endpoint` (
    `id` BIGINT NOT NULL,
    `label` VARCHAR ( 32 ) DEFAULT NULL COMMENT '标签',
    `pattern` VARCHAR ( 64 ) NOT NULL COMMENT 'url匹配模式',
    `method` enum ( 'GET', 'HEAD', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS', 'TRACE' ) DEFAULT NULL COMMENT '类型',
    `is_enabled` TINYINT UNSIGNED DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `tenant_id` INT DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB;-- 【关系表】菜单-端点

-- 【关系表】菜单-端点
DROP TABLE IF EXISTS `sys_menu_endpoint`;
CREATE TABLE `sys_menu_endpoint` (
     `menu_id` BIGINT ( 20 ) NOT NULL COMMENT '菜单ID',
     `endpoint_id` BIGINT ( 20 ) NOT NULL COMMENT '端点ID',
     PRIMARY KEY ( `menu_id`, `endpoint_id` ) USING BTREE
) ENGINE = INNODB;