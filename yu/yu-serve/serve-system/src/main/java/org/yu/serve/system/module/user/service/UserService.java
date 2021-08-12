package org.yu.serve.system.module.user.service;

import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.serve.system.module.user.dto.UserDTO;
import org.yu.serve.system.module.user.dto.UserFullDTO;
import org.yu.common.querydsl.service.DslBaseService;

/**
 * @author wangxd
 * @date 2020-11-09 09:58
 */
public interface UserService extends DslBaseService<UserDO, String> {

    UserDTO findDtoById(String userId);

    UserFullDTO getUserInfo(String userId);

}
