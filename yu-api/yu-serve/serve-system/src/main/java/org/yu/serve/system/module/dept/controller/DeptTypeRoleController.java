package org.yu.serve.system.module.dept.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.dept.domain.DeptTypeRoleDO;
import org.yu.serve.system.module.dept.service.DeptTypeRoleService;

/**
 * @author wangxd
 * @date 2020-11-30 17:57
 */
@RestController
@RequestMapping("deptTypeRole")
public class DeptTypeRoleController extends DslBaseApiController<DeptTypeRoleService, DeptTypeRoleDO, String> {
    protected DeptTypeRoleController(DeptTypeRoleService deptTypeRoleService) {
        super(deptTypeRoleService);
    }
}
