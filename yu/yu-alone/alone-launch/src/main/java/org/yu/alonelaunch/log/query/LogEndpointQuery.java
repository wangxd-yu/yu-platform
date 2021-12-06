package org.yu.alonelaunch.log.query;

import lombok.Data;
import org.yu.alonelaunch.log.domain.LogEndpointDO;
import org.yu.common.querydsl.query.annotation.YuOrderColumn;
import org.yu.common.querydsl.query.annotation.YuQuery;

/**
 * @author wangxd
 * @date 2021-12-04 22:15
 */
@Data
@YuQuery(domain = LogEndpointDO.class, orders = {@YuOrderColumn(column = "id", type = YuOrderColumn.Type.DESC)})
public class LogEndpointQuery {

    private String username;

    private String userId;

    private String ip;

    private String method;

    private String handler;

    private String pattern;

    private String url;

    private String request;

    private String response;

    private Integer httpStatus;

    /**
     * 耗时
     */
    private Integer time;
}
