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
@Table(name = "gen_api_dto")
public class GenApiDtoDO extends DslBaseDO<String> {
    @Basic
    @Column(name = "class_name", nullable = true, length = 64)
    private String className;
    @Basic
    @Column(name = "module_id", nullable = true)
    private Long moduleId;
    @Basic
    @Column(name = "domain_id", nullable = true)
    private Long domainId;
}
