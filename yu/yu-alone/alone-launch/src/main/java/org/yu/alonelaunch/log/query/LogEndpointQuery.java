package org.yu.alonelaunch.log.query;

import lombok.Data;
import org.yu.alonelaunch.log.domain.LogEndpointDO;
import org.yu.common.querydsl.query.annotation.*;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;

/**
 * @author wangxd
 * @date 2021-12-04 22:15
 */
@Data
@YuQuery(domain = LogEndpointDO.class,
        joins = @YuJoin(domain = EndpointDO.class, columns = @YuJoinColumn(column = "id", relationColumn = "endpointId")),
        orders = {@YuOrderColumn(column = "id", type = YuOrderColumn.Type.DESC)}
)
public class LogEndpointQuery {

    @YuQueryColumn(domain = EndpointDO.class, propName = "label", operator = YuOperatorEnum.INNER_LIKE)
    private String endpointName;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String username;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String ip;

    @YuQueryColumn
    private String method;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String handler;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String pattern;

    @YuQueryColumn
    private Integer httpStatus;

    /**
     * 耗时
     */
    private Integer time;
}
