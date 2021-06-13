package org.yu.serve.system.module.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wangxd
 * @date 2020-11-26 10:11
 */
@Getter
@Setter
public class UserRoleKeys implements Serializable {
    private Long userId;
    private Long roleId;
}
