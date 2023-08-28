package org.yu.generate.modules.api.domain.domain;

import cn.hutool.core.text.NamingCase;
import lombok.*;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.*;

/**
 * 数据库字段
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

    @Column
    private Long domainId;

    /**
     * 字段名（英文单字）
     */
    @Column
    private String name;

    /**
     * 字段类型
     */
    @Column
    private String type;

    /**
     * 字段长度
     */
    @Column
    private Integer length;

    /**
     * 小数点（小数位数）
     */
    @Column
    private Integer decimal;

    /**
     * 注释
     */
    @Column
    private String comment;

    /**
     * 排序
     */
    @Column
    private String sort;

    /**
     * 是否允许为空
     */
    @Column(name = "if_nullable")
    private String ifNullable;

    /**
     * java 属性类型
     */
    @Transient
    private String javaType;

    /**
     * 驼峰命名，纯小写的 字段名 ,例：user_age -> userAge
     */
    @Transient
    private String lowerName;

    /**
     * import 字段路径
     */
    @Transient
    private String importType;

    public String getLowerName() {
        return NamingCase.toCamelCase(name);
    }

}
