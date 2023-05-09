package org.yu.generate.modules.api.module.domain;

import lombok.*;
import org.yu.common.querydsl.domain.DslBaseDO;
import org.yu.generate.modules.api.domain.domain.GenApiDomainDO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author wangxd
 * @date 2023-04-13 23:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "gen_api_module")
public class GenApiModuleDO extends DslBaseDO<String> {
    @Basic
    @Column(name = "version", nullable = true, length = 20)
    private String version;
    @Basic
    @Column(name = "author", nullable = true, length = 20)
    private String author;
    @Basic
    @Column(name = "datetime", nullable = true)
    private Timestamp datetime;
    @Basic
    @Column(name = "module_name", nullable = true, length = 50)
    private String moduleName;
    @Basic
    @Column(name = "module_comment", nullable = true, length = 255)
    private String moduleComment;
    @Basic
    @Column(name = "query_id", nullable = false)
    private Long queryId;
    @Basic
    @Column(name = "package_path", nullable = true, length = 255)
    private String packagePath;

    @Transient
    private GenApiDomainDO genApiDomainDO;


}
