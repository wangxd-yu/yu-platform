package org.yu.serve.system.module.role.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wangxd
 * @date 2020-11-26 15:45
 */
@Getter
@Setter
@EqualsAndHashCode
public class RoleMenuKeys implements Serializable {
    private String roleId;
    private String menuId;
}