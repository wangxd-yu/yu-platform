package org.yu.serve.system.module.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2020-11-26 9:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(UserRoleKeys.class)
@Table(name="sys_user_role")
public class UserRoleDO {
    @Id
    private String userId;
    @Id
    private String roleId;
}
