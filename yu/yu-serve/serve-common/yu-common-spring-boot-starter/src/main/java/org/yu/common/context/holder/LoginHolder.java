package org.yu.common.context.holder;

import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.LoginUser;
import org.springframework.stereotype.Component;

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
    public LoginUser loginUser() {
        return YuContextHolder.getYuContext().getClientUser();
    }
}
