package org.yu.alonelaunch.log.query;

import lombok.Data;
import org.yu.alonelaunch.log.domain.LogLoginDO;
import org.yu.common.querydsl.query.annotation.YuOrderColumn;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;

/**
 * @author wangxd
 * @date 2021-11-24 23:17
 */
@Data
@YuQuery(domain = LogLoginDO.class, orders = {@YuOrderColumn(column = "id", type = YuOrderColumn.Type.DESC)})
public class LogLoginQuery {
    /**
     * 登录用户名
     */
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String username;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String ip;

    /**
     * 登录地点
     */
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String location;

    /**
     * 浏览器
     */
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String browser;

    /**
     * 操作系统
     */
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String os;
}
