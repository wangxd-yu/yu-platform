package org.yu.serve.system.module.dept.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 部门类型
 *
 * @author wangxd
 * @date 2020-11-30 14:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_dept_type")
public class DeptTypeDO extends DslBaseTenantDO<Long> {

    /**
     * 名称
     */
    private String name;

    /**
     * 编号
     */
    private String code;
}
