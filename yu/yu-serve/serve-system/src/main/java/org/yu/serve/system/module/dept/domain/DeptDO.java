package org.yu.serve.system.module.dept.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class DeptDO extends DslBaseTenantDO<Long> {
    /**
     * 上级部门no
     */
    private String pno;

    /**
     * 上下级关系编码
     */
    private String no;

    /**
     * 用户自定义编码
     */
    private String code;

    /**
     * 类型id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long typeId;

    /**
     * 类型编码
     */
    private String typeCode;

    /**
     * 部门名称
     */
    private String name;

    @Column(name = "is_enabled")
    private Boolean enabled;

    /**
     * 子部门数目
     */
    private Integer subCount;
}
