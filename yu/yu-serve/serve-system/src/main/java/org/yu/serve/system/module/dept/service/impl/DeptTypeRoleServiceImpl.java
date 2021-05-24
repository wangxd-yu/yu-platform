package org.yu.serve.system.module.dept.service.impl;

import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.dept.domain.DeptTypeRoleDO;
import org.yu.serve.system.module.dept.repository.DeptTypeRoleRepository;
import org.yu.serve.system.module.dept.service.DeptTypeRoleService;

/**
 * @author wangxd
 * @date 2020-11-30 15:02
 */
@Service
public class DeptTypeRoleServiceImpl extends DslBaseServiceImpl<DeptTypeRoleRepository, DeptTypeRoleDO, Long> implements DeptTypeRoleService {
}
