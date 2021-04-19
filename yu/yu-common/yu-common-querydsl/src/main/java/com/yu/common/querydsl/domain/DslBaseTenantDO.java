package com.yu.common.querydsl.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author wangxd
 * @date 2020-11-26
 */
@Getter
@Setter
@MappedSuperclass
public abstract class DslBaseTenantDO<ID extends Serializable> extends DslBaseDO<ID> {
    /**
     * 租户id
     */
    private Integer tenantId;
}
