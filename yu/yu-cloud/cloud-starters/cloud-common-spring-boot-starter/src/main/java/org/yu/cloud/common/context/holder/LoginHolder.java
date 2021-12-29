package org.yu.cloud.common.context.holder;

import org.springframework.stereotype.Component;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.SecurityUser;

/**
 * 登录信息
 *
 * @author wangxd
 */
@Component
public class LoginHolder {

    /**
     * 获取登录用户信息
     */
    public SecurityUser loginUser() {
        return YuContextHolder.getYuContext().getSecurityUser();
    }
}
