package org.yu.generate.modules.template.dto;

import lombok.Data;

/**
 * 模板代码 新增、修改 入参
 *
 * @author wangxd
 * @date 2024-03-05 23:11
 */
@Data
public class TemplateCodeDTO {
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
