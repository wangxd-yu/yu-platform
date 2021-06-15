package org.yu.serve.system.module.user.query;

import lombok.Getter;
import lombok.Setter;
import org.yu.common.querydsl.query.AbstractQuery;
import org.yu.common.querydsl.query.annotation.YuJoin;
import org.yu.common.querydsl.query.annotation.YuJoinColumn;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.user.domain.UserDO;

/**
 * @author wangxd
 * @date 2020-12-01 19:12
 */
@Getter
@Setter
@YuQuery(domain = UserDO.class, joins = {
        @YuJoin(domain = DeptDO.class, type = YuJoin.Type.LEFT_JOIN, columns = {
                @YuJoinColumn(column = "no", relationColumn = "deptNo")
        })
})
public class UserTableQuery extends AbstractQuery {
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String name;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String username;
}
