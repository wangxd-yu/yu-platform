package org.yu.generate.modules.template.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.generate.modules.template.domain.GenCodeTemplateDO;
import org.yu.generate.modules.template.domain.GenTemplateCodeDO;
import org.yu.generate.modules.template.domain.QGenCodeTemplateDO;
import org.yu.generate.modules.template.domain.QGenTemplateCodeDO;
import org.yu.generate.modules.template.dto.TemplateCodeDTO;
import org.yu.generate.modules.template.repository.GenCodeTemplateRepository;
import org.yu.generate.modules.template.repository.GenTemplateCodeRepository;

import java.time.LocalDateTime;

/**
 * @author wangxd
 * @date 2024-02-22 19:55
 */
@Service
public class GenCodeTemplateServiceImpl extends DslBaseServiceImpl<GenCodeTemplateRepository, GenCodeTemplateDO, String> implements GenCodeTemplateService {

    QGenCodeTemplateDO qGenCodeTemplateDO = QGenCodeTemplateDO.genCodeTemplateDO;
    QGenTemplateCodeDO qGenTemplateCodeDO = QGenTemplateCodeDO.genTemplateCodeDO;

    private GenTemplateCodeRepository genTemplateCodeRepository;

    public GenCodeTemplateServiceImpl(GenTemplateCodeRepository genTemplateCodeRepository) {
        this.genTemplateCodeRepository = genTemplateCodeRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCatalog(String id, String catalog) {
        this.getJPAQueryFactory().update(qGenCodeTemplateDO)
                .set(qGenCodeTemplateDO.catalog, catalog)
                .where(qGenCodeTemplateDO.id.eq(id))
                .execute();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GenTemplateCodeDO saveTemplateCode(TemplateCodeDTO templateCodeDTO) {
        GenTemplateCodeDO genTemplateCodeDO = this.getJPAQueryFactory()
                .selectFrom(qGenTemplateCodeDO)
                .where(
                        qGenTemplateCodeDO.templateId.eq(templateCodeDTO.getTemplateId()),
                        qGenTemplateCodeDO.catalogKey.eq(templateCodeDTO.getCatalogKey())
                ).fetchOne();
        // 修改
        if (genTemplateCodeDO != null) {
            genTemplateCodeDO.setCode(templateCodeDTO.getCode());
            genTemplateCodeDO.setUpdateTime(LocalDateTime.now());
        }
        //新增
        else {
            genTemplateCodeDO = new GenTemplateCodeDO();
            genTemplateCodeDO.setTemplateId(templateCodeDTO.getTemplateId());
            genTemplateCodeDO.setCatalogKey(templateCodeDTO.getCatalogKey());
            genTemplateCodeDO.setCode(templateCodeDTO.getCode());
        }
        return genTemplateCodeRepository.save(genTemplateCodeDO);
    }

    @Override
    public String getByTemplateAndKey(String templateId, String codeKey) {
        GenTemplateCodeDO genTemplateCodeDO = this.getJPAQueryFactory()
                .selectFrom(qGenTemplateCodeDO)
                .where(
                        qGenTemplateCodeDO.templateId.eq(templateId),
                        qGenTemplateCodeDO.catalogKey.eq(codeKey)
                ).fetchOne();
        if (genTemplateCodeDO == null) {
            return null;
        } else {
            return genTemplateCodeDO.getCode();
        }
    }
}
