package org.yu.serve.system.module.role.controller;

import cn.hutool.core.convert.Convert;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.role.domain.RoleDO;
import org.yu.serve.system.module.role.query.RoleQuery;
import org.yu.serve.system.module.role.service.RoleService;
import org.yu.serve.system.module.user.dto.UserTableDTO;
import org.yu.serve.system.module.user.query.UserRoleQuery;

import java.util.List;
import java.util.Map;

/**
 * @author wangxd
 * @date 2020-12-22 19:30
 */
@RestController
@RequestMapping("role")
public class RoleController extends DslBaseApiController<RoleService, RoleDO, String> {
    private final RoleService roleService;

    protected RoleController(RoleService roleService, RoleService roleService1) {
        super(roleService);
        this.roleService = roleService1;
    }

    @GetMapping
    public ResponseEntity<Object> getPage(RoleQuery query, Pageable pageable) {
        return super.query(query, pageable);
    }

    @PostMapping("{id}/menus")
    public ResponseEntity<Object> saveRoleMenus(@PathVariable String id, @RequestBody Map<String, List<String>> map) {
        roleService.saveRoleMenus(id, Convert.toStrArray(map.get("menuIds")));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/menus")
    public ResponseEntity<Object> getRoleMenus(@PathVariable String id) {
        return new ResponseEntity<>(roleService.getRoleMenus(id), HttpStatus.OK);
    }

    @GetMapping("{id}/users")
    public ResponseEntity<Object> getRoleUserPage(@PathVariable("id") String roleId, Pageable pageable) {
        UserRoleQuery query = new UserRoleQuery();
        query.setRoleId(roleId);
        return super.queryDTO(query, pageable, UserTableDTO.class);
    }
}
