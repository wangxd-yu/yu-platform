package org.yu.serve.system.module.dict.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;
import org.yu.common.web.valid.annotation.YuDependValid;
import org.yu.common.web.valid.annotation.YuUniqueValid;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2020-11-27 14:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_dict_detail")
@YuUniqueValid(props = {"dictId", "code"})
public class DictDetailDO extends DslBaseTenantDO<String> {
    /**
     * 字典id
     */
    @YuDependValid(domain = DictDO.class, prop = "id")
    private String dictId;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;
}
