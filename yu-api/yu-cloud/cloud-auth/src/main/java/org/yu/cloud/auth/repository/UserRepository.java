package org.yu.cloud.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yu.cloud.auth.domain.UserDO;

/**
 * @author wangxd
 * @date 2020-11-05 14:45
 */
public interface UserRepository extends JpaRepository<UserDO, String> {

    UserDO findByUsername(String username);
}
