package org.yu.serve.system.module.dict.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseTenantDO;

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
public class DictDetailDO extends DslBaseTenantDO<String> {
    /**
     * 字典id
     */
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
