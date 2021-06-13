package org.yu.serve.system.module.user.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2020-11-26 9:51
 */
@Data
@Entity
@IdClass(UserRoleKeys.class)
@Table(name="sys_user_role")
public class UserRoleDO {
    @Id
    private Long userId;
    @Id
    private Long roleId;
}
