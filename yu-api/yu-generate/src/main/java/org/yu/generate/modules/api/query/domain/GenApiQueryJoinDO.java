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
@Table(name = "gen_api_query_join")
public class GenApiQueryJoinDO extends DslBaseDO<String> {
    @Basic
    @Column(name = "query_id", nullable = false)
    private Long queryId;
    @Basic
    @Column(name = "domain_id", nullable = false)
    private Long domainId;
    @Basic
    @Column(name = "join_type", nullable = true)
    private String joinType;
}
