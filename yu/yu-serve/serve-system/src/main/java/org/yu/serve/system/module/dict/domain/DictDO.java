package org.yu.serve.system.module.dict.domain;

import lombok.Data;
import org.yu.common.querydsl.domain.DslBaseTenantDO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2020-11-27 14:06
 */
@Data
@Entity
@Table(name = "sys_dict")
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
