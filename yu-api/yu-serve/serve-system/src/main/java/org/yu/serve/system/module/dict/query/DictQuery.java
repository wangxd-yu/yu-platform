package org.yu.serve.system.module.dict.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.serve.system.module.dict.domain.DictDO;

/**
 * @author wangxd
 * @date 2020-12-01 20:04
 */
@Data
@YuQuery(domain = DictDO.class)
public class DictQuery {
    /**
     * 编号
     */
    @YuQueryColumn
    private String code;

    /**
     * 名称
     */
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String name;
}
