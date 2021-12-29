package org.yu.serve.system.module.dict.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.serve.system.module.dict.domain.DictDetailDO;

/**
 * @author wangxd
 * @date 2020-11-30 11:00
 */
@Data
@YuQuery(domain = DictDetailDO.class)
public class DictDetailQuery {
    @YuQueryColumn
    private String dictId;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String name;
}
