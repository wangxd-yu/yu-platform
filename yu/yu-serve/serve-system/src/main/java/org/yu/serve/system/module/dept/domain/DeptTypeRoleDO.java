package org.yu.serve.system.module.dept.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 部门类型上下级规则
 *
 * @author wangxd
 * @date 2020-11-30 14:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_dept_type_role")
public class DeptTypeRoleDO extends DslBaseTenantDO<Long> {

    /**
     * 主类型id
     */
    private Long typeId;

    /**
     * 子类型id
     */
    private Long subTypeId;
}
