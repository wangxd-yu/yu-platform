INSERT INTO `sys_dept`
VALUES (100, '000', '0', '3701', 1, 1, 'root', '中华人民共和国', 1, 6, '2020-11-30 17:21:36', '2020-11-30 17:21:39', 1),
       (101, '000002', '000', '3601', 2, 2, 'province', '河北省', 1, 0,'2020-12-01 09:39:58', '2020-12-01 09:39:58', 1),
       (102, '000003', '000', '1501', 3, 2, 'province', '广东省', 1, 0,'2020-12-01 09:42:21', '2020-12-01 09:42:21', 1),
       (103, '000004', '000', '3601', 4, 2, 'province', '四川省', 1, 0,'2020-12-03 15:00:49', '2020-12-03 15:00:49', 1),
       (104, '000006', '000', NULL, 6, 2, NULL, '黑龙江省', 1, 0, '2021-08-07 10:07:49','2021-08-07 10:07:49', 1000),
       (105, '000007', '000', NULL, 6, 2, NULL, '山东省', 1, 1, '2021-08-07 19:55:11','2021-08-07 19:55:11', 1000),
       (106, '000007001', '000007', NULL, 1, 3, NULL, '济南市', 1, 0,'2021-08-07 19:55:30', '2021-08-07 19:55:30', 1000);

INSERT INTO `sys_dept_type`
VALUES (1, 'root', '国家', '2020-11-30 18:31:25', '2020-11-30 18:31:25', 1),
       (2, 'province', '省', '2020-11-30 18:36:29', '2020-11-30 18:36:29', 1),
       (3, 'city', '市', '2020-11-30 18:36:45', '2020-11-30 18:36:45', 1),
       (4, 'municipality', '直辖市', '2020-11-30 18:38:58', '2020-11-30 18:38:58', 1),
       (5, 'autonomous', '自治区', '2020-11-30 18:39:23', '2020-11-30 18:39:23', 1);


INSERT INTO `sys_dept_type_role`
VALUES (1, 0, 1, '2021-08-07 14:38:39', '2021-08-07 14:38:44', 1),
       (2, 2, 3, '2021-08-07 23:17:11', '2021-08-07 23:17:14', 1),
       (3, 1, 2, '2020-11-30 18:40:29', '2020-11-30 18:40:29', 1),
       (4, 1, 3, '2020-11-30 18:40:51', '2020-11-30 18:40:51', 1),
       (5, 1, 4, '2020-11-30 18:41:00', '2020-11-30 18:41:00', 1),
       (6, 1, 5, '2020-11-30 18:41:08', '2020-11-30 18:41:08', 1);

INSERT INTO `sys_dict`
VALUES (1, 'sex', '性别', '人员性别', '2020-11-27 15:03:17', '2020-11-27 15:03:17', 1),
       (2, 'enabled_status', '启停状态', '启停状态', '2021-07-09 15:10:59', '2020-12-02 18:40:28', NULL);

INSERT INTO `sys_dict_detail`
VALUES (1, 1, '1', '1', 1, '2021-11-01 22:33:04', '2021-11-01 22:33:04', NULL),
       (2, 1, 'male', '男', 1, '2020-11-27 15:48:34', '2020-12-02 18:03:13', 1),
       (3, 1, 'female', '女', 2, '2020-11-27 15:48:34', '2020-11-27 15:48:34', 1),
       (4, 2, 'true', '正常', 1, '2020-11-27 15:48:34', '2020-12-02 19:06:42', 1),
       (5, 2, 'false', '停用', 2, '2020-11-27 15:48:34', '2020-12-02 19:06:49', 1);

INSERT INTO `sys_endpoint`
VALUES (1, '新增用户', '/user', 'POST', 0, '2021-10-16 23:43:50', '2021-10-16 23:43:50', NULL, 1),
       (2, '菜单新增', '/menu', 'POST', 1, '2021-10-17 13:51:33', '2021-10-17 13:51:33', NULL, 1),
       (3, '菜单修改', '/menu', 'PUT', 1, '2021-10-17 13:51:48', '2021-10-17 13:51:48', NULL, 1),
       (4, '查询用户-列表', '/user', 'GET', 1, '2021-10-18 22:06:00', '2021-10-18 22:06:00', NULL, 1),
       (5, '字典查询-详情', '/dict/{id}', 'GET', 1, '2021-10-19 23:17:55', '2021-10-19 23:17:55', NULL, 1),
       (6, '字典删除', '/dict/{id}', 'DELETE', 1, '2021-10-19 23:20:45', '2021-10-19 23:20:45', NULL, 1),
       (7, '字典详情新增', '/dictDetail', 'POST', 0, '2021-12-01 23:41:03', '2021-12-01 23:41:03', NULL, 1),
       (8, '字典明细查询', '/dictDetail', 'GET', NULL, '2021-12-03 22:13:06', '2021-12-03 22:13:06', NULL,1),
       (9, '字典明细删除', '/dictDetail/{id}', 'DELETE', 1, '2021-12-05 00:00:30', '2021-12-05 00:00:30',NULL, 1),
       (10, '字典列表', '/dict', 'GET', 1, '2021-12-07 16:08:42', '2021-12-07 16:08:42', NULL, 1);

INSERT INTO `sys_menu`
VALUES (1, 0, '系统管理', 'FOLDER', '/system', NULL, NULL, 1, 'crown', 0, '2018-12-18 15:11:29', NULL, NULL),
       (2, 1, '用户管理', 'MENU', '/system/user', './system/User', NULL, 2, NULL, 0, '2018-12-18 15:14:44', NULL, NULL),
       (3, 1, '角色管理', 'MENU', '/system/role', './system/Role', NULL, 3, NULL, 0, '2018-12-18 15:16:07', NULL, NULL),
       (4, 1, '菜单管理', 'MENU', '/system/menu', './system/Menu', NULL, 5, NULL, 0, '2018-12-18 15:17:28', NULL, NULL),
       (5, 1, '部门管理', 'MENU', '/system/dept', './system/Dept', NULL, 6, NULL, 0, '2019-03-25 09:46:00', NULL, NULL),
       (6, 1, '字典管理', 'MENU', '/system/dict', './system/Dict', NULL, 8, NULL, 0, '2019-04-10 11:49:04','2021-09-14 21:41:43', NULL),
       (7, 1, '端点管理', 'MENU', '/system/endpoint', './system/Dict', NULL, 9, NULL, 0, '2021-09-14 12:49:09','2021-09-14 21:35:19', NULL),
       (8, 0, '日志管理', 'FOLDER', '/log', NULL, NULL, 2, 'yuque', 0, '2021-11-22 23:17:57','2021-11-23 23:30:18', NULL),
       (9, 8, '登录日志', 'MENU', '/log/login', './log/LoginLog', NULL, 1, NULL, NULL,'2021-11-22 23:31:48', '2021-11-22 23:31:48', NULL),
       (10, 8, '接口日志', 'MENU', '/log/endpoint', './log/EndpointLog', NULL, 2, NULL,NULL, '2021-11-22 23:32:23', '2021-11-22 23:32:23', NULL);

INSERT INTO `sys_menu_endpoint`
VALUES (1, 1);

INSERT INTO `sys_role`
VALUES (1, 'admin_', '管理员', '管理员角色', '2021-08-08 03:05:17', '2021-08-08 03:05:17', NULL),
       (2, 'visitor', '访客', '访客角色', '2021-06-18 03:13:52', '2021-08-09 22:25:14', NULL);

INSERT INTO `sys_role_menu`
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10);

INSERT INTO `sys_user`
VALUES (1, 'admin', '$2a$10$gHRTStCK9r8FRv4yl4tRZOBzDrSCK1.Q5pNI.SbA4UsA1Uw5KtR4m', 1, '管理员', '2020-11-06 14:06:42','2020-11-06 14:06:44', '2020-11-06 14:06:47', '000', 1),
       (2, 'visitor', '$2a$10$WBb/ADOZxsw08/VP1O1bbeHiQbIrQd4KnylekWiPoOnZblVYekLia', 1, '访客','2021-08-12 04:04:21', '2021-11-17 08:23:50', NULL, '000007001', NULL);

INSERT INTO `sys_user_role`
VALUES (1, 1),
       (2, 2);