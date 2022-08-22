package org.yu.serve.system.module.virtualdept.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 虚拟部门表
 *
 * @author wangxd
 */
@Data
@Entity
@Table(name = "sys_virtual_dept")
@EqualsAndHashCode(callSuper = true)
public class VirtualDeptDO extends DslBaseDO<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 上级部门id
     */
    @Column(name = "pid", nullable = false)
    private String pid;

    /**
     * 类型：0：部门；1：人员
     */
    @Column(name = "type")
    private String type;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 部门全称
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * 状态：1启用、0禁用
     */
    @Column(name = "is_enabled")
    private Boolean enabled;

    /**
     * 子部门数目
     */
    @Column(name = "sub_count")
    private Integer subCount;

}
