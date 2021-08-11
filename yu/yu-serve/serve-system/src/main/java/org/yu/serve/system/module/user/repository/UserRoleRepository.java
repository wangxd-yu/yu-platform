package org.yu.serve.system.module.user.repository;

import org.yu.common.querydsl.repository.DslBaseRepository;
import org.yu.serve.system.module.user.domain.UserRoleDO;
import org.yu.serve.system.module.user.domain.UserRoleKeys;

/**
 * @author wangxd
 * @date 2021-08-11 13:30
 */
public interface UserRoleRepository extends DslBaseRepository<UserRoleDO, UserRoleKeys> {
}
