package org.yu.serve.system.module.dict.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;
import org.yu.common.querydsl.valid.annotation.YuUniqueValid;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2020-11-27 14:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_dict")
@YuUniqueValid(props = "code")
public class DictDO extends DslBaseTenantDO<String> {
    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
