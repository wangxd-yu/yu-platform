package org.yu.serve.system.module.role.service;

import org.springframework.http.ResponseEntity;
import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.role.domain.RoleDO;

import java.util.Set;

/**
 * @author wangxd
 * @date 2020-12-22 19:26
 */
public interface RoleService extends DslBaseService<RoleDO, Long> {
    void saveRoleMenus(Long roleId, Long[] menuIds);

    Set<String> getRoleMenus(Long roleId);
}
