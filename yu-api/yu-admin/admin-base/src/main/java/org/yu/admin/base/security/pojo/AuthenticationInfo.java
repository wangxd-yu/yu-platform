package org.yu.admin.base.security.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录成功返回信息
 *
 * @author wangxd
 * @date 2021-12-10 16:54
 */
@Data
@Builder
public class AuthenticationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String token;

    private final String refreshToken;

    private final String tokenHead;

    /**
     * 有效时间 （单位：秒）
     */
    private final Long expiresIn;

    /**
     * 超时时间点（单位：秒）
     */
    private final Long expiration;
}
