package org.yu.serve.system.module.user.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wangxd
 * @date 2020-11-26 10:11
 */
@Getter
@Setter
@EqualsAndHashCode
public class UserRoleKeys implements Serializable {
    private String userId;
    private String roleId;
}
