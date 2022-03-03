package org.yu.common.core.util;

import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.SecurityUser;

import java.util.Optional;
import java.util.Set;

/**
 * @author wangxd
 * @date 2021-12-24 10:10
 */
public class SecurityUtil {
    /**
     * 获取用户信息
     */
    public static SecurityUser getSecurityUser() {
        return YuContextHolder.getYuContext() == null ? null : YuContextHolder.getYuContext().getSecurityUser();
    }

    /**
     * 获取用户id
     */
    public static String getUserId() {
        return Optional.ofNullable(getSecurityUser())
                .map(SecurityUser::getId)
                .orElse(null);
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        return Optional.ofNullable(getSecurityUser())
                .map(SecurityUser::getUsername)
                .orElse(null);
    }

    /**
     * 获取部门编码
     */
    public static String getDeptId() {
        return Optional.ofNullable(getSecurityUser())
                .map(SecurityUser::getDeptId)
                .orElse(null);
    }

    /**
     * 获取角色列表
     */
    public static Set<String> getRoles() {
        return Optional.ofNullable(getSecurityUser())
                .map(SecurityUser::getRoles)
                .orElse(null);
    }
}
