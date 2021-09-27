package org.yu.serve.system.module.endpoint.query;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;

/**
 * 端点-查询条件
 *
 * @author wangxd
 * @date 2021-09-27
 */
@Data
@YuQuery(domain = EndpointDO.class)
public class EndpointQuery {

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String label;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String pattern;

    @YuQueryColumn
    private RequestMethod method;
}
