package org.yu.serve.system.module.menu.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 菜单-端点 key
 *
 * @author wangxd
 * @date 2021-09-10
 */
@Getter
@Setter
@EqualsAndHashCode
public class MenuEndpointKeys implements Serializable {
    private String menuId;
    private String endpointId;
}
