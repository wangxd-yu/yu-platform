package org.yu.generate.modules.api.domain.domain;

import cn.hutool.core.text.NamingCase;
import lombok.*;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.*;

/**
 * @author wangxd
 * @date 2023-04-13 23:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "gen_api_domain_field")
public class GenApiDomainFieldDO extends DslBaseDO<String> {
    @Basic
    @Column(name = "domain_id", nullable = true)
    private Long domainId;
    @Basic
    @Column(name = "column_name", nullable = false, length = 20)
    private String columnName;
    @Basic
    @Column(name = "column_type", nullable = true, length = 20)
    private String columnType;
    @Basic
    @Column(name = "column_comment", nullable = true, length = 255)
    private String columnComment;
    @Basic
    @Column(name = "ordinal_position", nullable = true)
    private String ordinalPosition;
    @Basic
    @Column(name = "if_nullable", nullable = true)
    private String ifNullable;

    /**
     * java 属性类型
     */
    @Transient
    private String javaType;

    @Transient
    private String lowerColumnName;

    /**
     * import 字段路径
     */
    @Transient
    private String importType;

    public String getLowerColumnName() {
        return NamingCase.toCamelCase(columnName);
    }

}
