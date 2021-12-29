package org.yu.serve.system.module.menu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 菜单-端点 关系
 *
 * @author wangxd
 * @date 2021-09-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(value = MenuEndpointKeys.class)
@Table(name = "sys_menu_endpoint")
public class MenuEndpointDO {
    @Id
    private String menuId;

    @Id
    private String endpointId;
}
