-- 【日志表】端点日志表
DROP TABLE IF EXISTS `log_endpoint`;
CREATE TABLE `log_endpoint`
(
    `id`          bigint unsigned NOT NULL COMMENT 'id',
    `endpoint_id` bigint unsigned NOT NULL COMMENT '端点id',
    `user_id`     bigint unsigned NOT NULL COMMENT '用户id',
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '用户名',
    `method`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT '请求方法',
    `handler`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '处理器',
    `pattern`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '端点路径',
    `url`         varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '提交url',
    `request`     json     DEFAULT NULL COMMENT '请求数据',
    `response`    json     DEFAULT NULL COMMENT '响应数据',
    `http_status` smallint                                                NOT NULL COMMENT '响应状态',
    `ip`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin   NOT NULL COMMENT 'ip地址',
    `time`        smallint unsigned DEFAULT NULL COMMENT '耗时',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int      DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='登录日志表';

-- 【日志表】登录日志表
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login`
(
    `id`          bigint NOT NULL COMMENT 'ID',
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
    `ip`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'ip',
    `location`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'ip归属地',
    `browser`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '浏览器',
    `os`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作系统',
    `dept_no`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '部门no',
    `tenant_id`   int                                                   DEFAULT NULL COMMENT '租户ID',
    `create_time` datetime                                              DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                              DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='端点日志表';

-- 部门表
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`          bigint                                                NOT NULL,
    `no`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '上下级关系编码',
    `pid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '上级部门no',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户自定义编码',
    `sort`        tinyint                                               DEFAULT NULL COMMENT '排序',
    `type_id`     bigint                                                DEFAULT NULL COMMENT '类型id',
    `type_code`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型编号',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
    `is_enabled`  tinyint unsigned DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `sub_count`   int                                                   DEFAULT '0' COMMENT '子部门数目',
    `create_time` datetime                                              DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                              DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int                                                   DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='部门表';

-- 部门类型表
DROP TABLE IF EXISTS `sys_dept_type`;
CREATE TABLE `sys_dept_type`
(
    `id`          bigint NOT NULL,
    `code`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编号',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
    `create_time` datetime                                              DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                              DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int                                                   DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='部门类型表';

-- 部门类型规则表
DROP TABLE IF EXISTS `sys_dept_type_role`;
CREATE TABLE `sys_dept_type_role`
(
    `id`          bigint NOT NULL,
    `type_id`     bigint NOT NULL COMMENT '主类型id',
    `sub_type_id` bigint NOT NULL COMMENT '子类型id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int      DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='部门类型规则表';

-- 字典表-主表
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint NOT NULL,
    `code`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL COMMENT '编号',
    `name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL COMMENT '名称',
    `remark`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `create_time` datetime                                               DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                               DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int                                                    DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='字典表-主表';

-- 字典表-明细表
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail`
(
    `id`          bigint NOT NULL,
    `dict_id`     bigint NOT NULL,
    `code`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编号',
    `name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
    `sort`        tinyint                                               DEFAULT NULL COMMENT '排序',
    `create_time` datetime                                              DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                              DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int                                                   DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='字典表-明细表';

-- 端点表
DROP TABLE IF EXISTS `sys_endpoint`;
CREATE TABLE `sys_endpoint`
(
    `id`                bigint                                                NOT NULL,
    `label`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标签',
    `pattern`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'url匹配模式',
    `method`            enum('GET','HEAD','POST','PUT','PATCH','DELETE','OPTIONS','TRACE') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型',
    `is_access_enabled` tinyint unsigned DEFAULT NULL COMMENT '权限状态：1启用、0禁用',
    `create_time`       datetime                                              DEFAULT NULL COMMENT '创建时间',
    `update_time`       datetime                                              DEFAULT NULL COMMENT '更新时间',
    `tenant_id`         int                                                   DEFAULT NULL COMMENT '租户ID',
    `is_log_enabled`    tinyint unsigned DEFAULT NULL COMMENT '日志状态：1启用、0禁用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='端点表';

-- 菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint NOT NULL COMMENT 'ID',
    `pid`         bigint NOT NULL COMMENT '上级菜单ID',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单名称',
    `type`        enum('FOLDER','MENU','PERMISSION') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型',
    `path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接地址',
    `component`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组件',
    `permission`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限编码',
    `sort`        int                                                    DEFAULT NULL COMMENT '排序',
    `icon`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
    `is_frame`    tinyint unsigned DEFAULT NULL COMMENT '是否外链',
    `create_time` datetime                                               DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                               DEFAULT NULL COMMENT '更新时间',
    `tenant_id`   int                                                    DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单表';

-- 【关系表】菜单-端点
DROP TABLE IF EXISTS `sys_menu_endpoint`;
CREATE TABLE `sys_menu_endpoint`
(
    `menu_id`     bigint NOT NULL COMMENT '菜单ID',
    `endpoint_id` bigint NOT NULL COMMENT '端点ID',
    PRIMARY KEY (`menu_id`, `endpoint_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='【关系表】菜单-端点';

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint NOT NULL COMMENT 'ID',
    `code`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL COMMENT '编号',
    `name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  DEFAULT NULL COMMENT '名称',
    `remark`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `create_time` datetime                                               DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                               DEFAULT NULL COMMENT '创建时间',
    `tenant_id`   int unsigned DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色表';

-- 【关系表】角色-菜单
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='【关系表】角色-菜单';

-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`                   bigint NOT NULL COMMENT 'ID',
    `username`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
    `password`             varchar(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
    `is_enabled`           tinyint unsigned DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `email`                varchar(32) DEFAULT NULL COMMENT '邮箱',
    `name`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '姓名',
    `portrait_url`         varchar(256)                                          DEFAULT NULL COMMENT '头像地址',
    `create_time`          datetime                                              DEFAULT NULL COMMENT '创建时间',
    `update_time`          datetime                                              DEFAULT NULL COMMENT '创建时间',
    `password_update_time` datetime                                              DEFAULT NULL COMMENT '密码修改时间',
    `dept_no`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '部门NO',
    `tenant_id`            int unsigned DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';

-- 【关系表】用户-角色
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='【关系表】用户-角色';