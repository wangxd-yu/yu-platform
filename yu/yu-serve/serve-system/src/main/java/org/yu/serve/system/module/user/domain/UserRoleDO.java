package org.yu.serve.system.module.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(name = "user_id")
    private String userId;
    @Id
    @Column(name = "role_id")
    private String roleId;
}
