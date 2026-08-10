package org.yu.generate.modules.template.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.generate.modules.template.domain.GenCodeTemplateDO;

/**
 * @author wangxd
 * @date 2024-02-22 19:54
 */
@Data
@YuQuery(domain = GenCodeTemplateDO.class)
public class GenCodeTemplateQuery {
    /**
     * 名称
     */
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String name;
}
