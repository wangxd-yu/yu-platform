package org.yu.serve.system.module.user.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.serve.system.module.user.dto.UserBaseInfo;
import org.yu.serve.system.module.user.dto.UserDTO;
import org.yu.serve.system.module.user.dto.UserFullDTO;

import java.util.Set;

/**
 * @author wangxd
 * @date 2020-11-09 09:58
 */
public interface UserService extends DslBaseService<UserDO, String> {

    UserDTO findDtoById(String userId);

    UserFullDTO getUserInfo(String userId);

    Set<String> getRoleCodesByUserId(String userId);

    void changeEnabled(String id, boolean enabled);

    void updateBaseInfo(UserBaseInfo userBaseInfo);
}
