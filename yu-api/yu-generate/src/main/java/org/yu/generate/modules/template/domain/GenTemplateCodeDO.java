package org.yu.generate.modules.template.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2024-03-05 22:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "gen_template_code")
public class GenTemplateCodeDO extends DslBaseDO<String> {

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 目录key
     */
    private String catalogKey;

    /**
     * 模版代码
     */
    private String code;

}
