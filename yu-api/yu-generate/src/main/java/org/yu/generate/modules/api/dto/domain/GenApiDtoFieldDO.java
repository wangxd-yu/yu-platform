package org.yu.generate.modules.api.dto.domain;

import lombok.*;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2023-04-13 23:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "gen_api_dto_field")
public class GenApiDtoFieldDO extends DslBaseDO<String> {
    @Basic
    @Column(name = "dto_id", nullable = true)
    private Long dtoId;
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
    @Column(name = "template", nullable = true, length = 255)
    private String template;
}
