package org.yu.serve.system.module.menu.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2020-11-26 13:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_menu")
public class MenuDO extends DslBaseTenantDO<Long> {
    /**
     * 上级菜单ID
     */
    private Long pid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 组件
     */
    private String component;

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

    /**
     * 链接地址
     */
    private String path;
}