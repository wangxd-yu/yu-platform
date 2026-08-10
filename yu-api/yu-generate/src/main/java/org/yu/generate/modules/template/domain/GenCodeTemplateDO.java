package org.yu.generate.modules.template.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2024-02-22 19:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "gen_code_template")
public class GenCodeTemplateDO extends DslBaseDO<String> {

    /**
     * 名称
     */
    private String name;

    /**
     * 模版代码
     */
    private String code;

    /**
     * 代码目录
     */
    private String catalog;

    /**
     * 版本
     */
    private String version;

    /**
     * 是否可用
     */
    private Boolean enableFlag;

}
