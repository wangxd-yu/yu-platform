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
@Table(name = "gen_api_query")
public class GenApiQueryDO extends DslBaseDO<String> {
    @Basic
    @Column(name = "domain_id", nullable = true)
    private Long domainId;
    @Basic
    @Column(name = "version", nullable = true, length = 20)
    private String version;
    @Basic
    @Column(name = "comment", nullable = true, length = 255)
    private String comment;
}
