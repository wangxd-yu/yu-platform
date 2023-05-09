package org.yu.generate.modules.api.domain.domain;

import cn.hutool.core.text.NamingCase;
import lombok.*;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "gen_api_domain")
public class GenApiDomainDO extends DslBaseDO<String> {
    @Basic
    @Column(name = "version", length = 20)
    private String version;
    @Basic
    @Column(name = "module_id")
    private Long moduleId;
    @Basic
    @Column(name = "domian_name", length = 20)
    private String domainName;
    @Basic
    @Column(name = "table_name", length = 20)
    private String tableName;
    @Basic
    @Column(name = "table_comment", length = 255)
    private String tableComment;

    /**
     * 首字母大写 domain 名称
     */
    @Transient
    private String upperDomainName;

    /**
     * 首字母小写 domain 名称
     */
    @Transient
    private String lowerDomainName;
    /**
     * domain 属性列表
     */
    @Transient
    private List<GenApiDomainFieldDO> domainFields;

    public String getUpperDomainName() {
        return NamingCase.toPascalCase(domainName);
    }

    public String getLowerDomainName() {
        return NamingCase.toCamelCase(domainName);
    }
}
