package org.yu.serve.system.module.dept.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuJoin;
import org.yu.common.querydsl.query.annotation.YuJoinColumn;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.domain.DeptTypeDO;

/**
 * @author wangxd
 * @date 2020-12-02 13:47
 */
@Data
@YuQuery(domain = DeptDO.class, joins = @YuJoin(domain = DeptTypeDO.class, columns = @YuJoinColumn(column = "id", relationColumn = "typeId")))
public class DeptQuery {

    @YuQueryColumn(operator = YuOperatorEnum.START_WITH)
    private String no;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String name;

    @YuQueryColumn
    private String pno;

}
