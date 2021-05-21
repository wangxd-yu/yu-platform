package org.yu.serve.system.module.role.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.role.domain.RoleDO;
import org.yu.serve.system.module.role.query.RoleQuery;
import org.yu.serve.system.module.role.service.RoleService;

/**
 * @author wangxd
 * @date 2020-12-22 19:30
 */
@RestController
@RequestMapping("role")
public class RoleController extends DslBaseApiController<RoleService, RoleDO, Long> {
    protected RoleController(RoleService dslBaseService) {
        super(dslBaseService);
    }

    @GetMapping
    public ResponseEntity<Object> getPage(RoleQuery query, Pageable pageable) {
        return super.query(query, pageable);
    }
}
