package org.yu.serve.system.module.role.service.impl;

import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.role.domain.RoleDO;
import org.yu.serve.system.module.role.repository.RoleRepository;
import org.yu.serve.system.module.role.service.RoleService;

/**
 * @author wangxd
 * @date 2020-12-22 19:28
 */
@Service
public class RoleServiceImpl extends DslBaseServiceImpl<RoleRepository, RoleDO, Long> implements RoleService {
}
