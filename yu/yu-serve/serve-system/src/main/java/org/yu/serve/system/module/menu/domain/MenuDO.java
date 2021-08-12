package org.yu.serve.system.module.menu.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;
import org.yu.serve.system.module.menu.eumus.MenuTypeEnum;

import javax.persistence.*;

/**
 * @author wangxd
 * @date 2020-11-26 13:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_menu")
public class MenuDO extends DslBaseTenantDO<String> {
    /**
     * 上级菜单ID
     */
    private String pid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单类型
     */
    @Enumerated(EnumType.STRING)
    private MenuTypeEnum type;

    /**
     * 组件
     */
    private String component;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 权限名称
     */
    private String permission;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否外链
     */
    @Column(name = "is_frame")
    private Boolean frame;
}
