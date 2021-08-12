package org.yu.serve.system.module.role.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2020-11-26 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(value = RoleMenuKeys.class)
@Table(name = "sys_role_menu")
public class RoleMenuDO {
    @Id
    private String roleId;
    @Id
    private String menuId;
}
