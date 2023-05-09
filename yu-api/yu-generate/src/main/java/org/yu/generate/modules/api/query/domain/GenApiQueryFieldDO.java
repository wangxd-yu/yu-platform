package org.yu.generate.modules.api.query.domain;

import lombok.*;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2023-04-13 23:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "gen_api_query_field")
public class GenApiQueryFieldDO extends DslBaseDO<String> {
    @Basic
    @Column(name = "query_id", nullable = true)
    private Long queryId;
    @Basic
    @Column(name = "field_name", nullable = true, length = 64)
    private String fieldName;
    @Basic
    @Column(name = "domain_id", nullable = true)
    private Long domainId;
    @Basic
    @Column(name = "prop_name", nullable = true, length = 64)
    private String propName;
    @Basic
    @Column(name = "operator", nullable = true, length = 20)
    private String operator;
}
