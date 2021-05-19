package org.yu.serve.system.module.role.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.serve.system.module.role.domain.RoleDO;

/**
 * @author wangxd
 * @date 2020-12-22 19:33
 */
@Data
@YuQuery(domain = RoleDO.class)
public class RoleQuery {
}
