package org.yu.serve.system.module.dept.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2020-11-30 13:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_dept")
public class DeptDO extends DslBaseTenantDO<String> {
    /**
     * 上级部门no
     */
    private String pid;

    /**
     * 用户自定义编码
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型id
     */
    private String typeId;

    /**
     * 类型编码
     */
    private String typeCode;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门全名
     */
    private String fullName;

    @Column(name = "is_enabled")
    private Boolean enabled;

    /**
     * 子部门数目
     */
    private Integer subCount;
}
