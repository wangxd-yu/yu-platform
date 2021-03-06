package org.yu.serve.system.module.role.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;
import org.yu.common.web.valid.annotation.YuUniqueValid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangxd
 * @date 2020-11-25 19:55
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_role")
@YuUniqueValid(props = "code", message = "已存在角色编号：${code}")
public class RoleDO extends DslBaseTenantDO<String> implements Serializable {
    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @Column
    private String remark;
}
