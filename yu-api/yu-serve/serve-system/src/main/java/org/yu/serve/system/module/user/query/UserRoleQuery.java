package org.yu.serve.system.module.user.query;

import lombok.Getter;
import lombok.Setter;
import org.yu.common.querydsl.query.AbstractQuery;
import org.yu.common.querydsl.query.annotation.YuJoin;
import org.yu.common.querydsl.query.annotation.YuJoinColumn;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.user.domain.UserDO;
import org.yu.serve.system.module.user.domain.UserRoleDO;

/**
 * @author wangxd
 * @date 2020-12-01 19:12
 */
@Getter
@Setter
@YuQuery(domain = UserDO.class, joins = {
        @YuJoin(domain = DeptDO.class, type = YuJoin.Type.LEFT_JOIN, columns = {
                @YuJoinColumn(column = "id", relationColumn = "deptId")
        }),
        @YuJoin(domain = UserRoleDO.class, type = YuJoin.Type.INNER_JOIN, columns = {
                @YuJoinColumn(column = "userId", relationColumn = "id")}
        )
})
public class UserRoleQuery extends AbstractQuery {
    @YuQueryColumn(domain = UserRoleDO.class)
    private String roleId;

    @YuQueryColumn
    private Boolean enabled;
}
