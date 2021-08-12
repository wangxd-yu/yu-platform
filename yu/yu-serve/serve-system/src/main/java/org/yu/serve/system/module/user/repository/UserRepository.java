package org.yu.serve.system.module.user.repository;

import org.springframework.stereotype.Repository;
import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.common.querydsl.repository.DslBaseRepository;

/**
 * @author wangxd
 * @date 2020-11-09 10:00
 */
@Repository
public interface UserRepository extends DslBaseRepository<UserDO, String> {
    UserDO findByUsername(String username);
}
