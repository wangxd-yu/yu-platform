package org.yu.generate.modules.template.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.generate.modules.template.domain.GenCodeTemplateDO;
import org.yu.generate.modules.template.domain.GenTemplateCodeDO;
import org.yu.generate.modules.template.dto.TemplateCodeDTO;

/**
 * @author wangxd
 * @date 2024-02-22 19:55
 */
public interface GenCodeTemplateService extends DslBaseService<GenCodeTemplateDO, String> {
    void updateCatalog(String id, String catalog);

    GenTemplateCodeDO saveTemplateCode(TemplateCodeDTO templateCodeDTO);

    String getByTemplateAndKey(String templateId, String codeKey);
}
