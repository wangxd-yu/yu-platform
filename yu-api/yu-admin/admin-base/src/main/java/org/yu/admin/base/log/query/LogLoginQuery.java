package org.yu.admin.base.log.query;

import lombok.Data;
import org.yu.admin.base.log.domain.LogLoginDO;
import org.yu.common.querydsl.query.AbstractQuery;
import org.yu.common.querydsl.query.annotation.*;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.serve.system.module.dept.domain.DeptDO;

/**
 * @author wangxd
 * @date 2021-11-24 23:17
 */
@Data
@YuQuery(domain = LogLoginDO.class,
        joins = {
                @YuJoin(domain = DeptDO.class, type = YuJoin.Type.LEFT_JOIN, columns = {
                        @YuJoinColumn(column = "id", relationColumn = "deptId")
                })},
        orders = {@YuOrderColumn(column = "id", type = YuOrderColumn.Type.DESC)})
public class LogLoginQuery extends AbstractQuery {
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
