package org.yu.serve.system.module.role.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.role.domain.QRoleMenuDO;
import org.yu.serve.system.module.role.domain.RoleDO;
import org.yu.serve.system.module.role.domain.RoleMenuDO;
import org.yu.serve.system.module.role.repository.RoleMenuRepository;
import org.yu.serve.system.module.role.repository.RoleRepository;
import org.yu.serve.system.module.role.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author wangxd
 * @date 2020-12-22 19:28
 */
@Service
public class RoleServiceImpl extends DslBaseServiceImpl<RoleRepository, RoleDO, String> implements RoleService {
    private final QRoleMenuDO qRoleMenuDO = QRoleMenuDO.roleMenuDO;

    private final RoleMenuRepository roleMenuRepository;

    public RoleServiceImpl(RoleMenuRepository roleMenuRepository) {
        this.roleMenuRepository = roleMenuRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenus(String roleId, String[] menuIds) {
        super.getJPAQueryFactory().delete(qRoleMenuDO)
                .where(qRoleMenuDO.roleId.eq(roleId))
                .execute();
        List<RoleMenuDO> roleMenuDOList = new ArrayList<>(menuIds.length);
        for (String menuId : menuIds) {
            roleMenuDOList.add(new RoleMenuDO(roleId, menuId));
        }
        roleMenuRepository.saveAll(roleMenuDOList);
    }

    @Override
    public Set<String> getRoleMenus(String roleId) {
        return StreamSupport.stream(roleMenuRepository.findAll(qRoleMenuDO.roleId.eq(roleId)).spliterator(), false)
                .map(item -> item.getMenuId().toString()).collect(Collectors.toSet());
    }
}
