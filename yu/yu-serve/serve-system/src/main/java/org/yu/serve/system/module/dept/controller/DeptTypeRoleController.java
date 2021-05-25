package org.yu.serve.system.module.dept.controller;

import org.yu.serve.system.module.dept.domain.DeptTypeRoleDO;
import org.yu.serve.system.module.dept.service.DeptTypeRoleService;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxd
 * @date 2020-11-30 17:57
 */
@RestController
@RequestMapping("deptTypeRole")
public class DeptTypeRoleController extends DslBaseApiController<DeptTypeRoleService, DeptTypeRoleDO, Long> {
    protected DeptTypeRoleController(DeptTypeRoleService deptTypeRoleService) {
        super(deptTypeRoleService);
    }
}
