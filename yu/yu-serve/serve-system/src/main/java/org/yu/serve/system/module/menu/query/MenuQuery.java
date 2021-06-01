package org.yu.serve.system.module.menu.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.serve.system.module.menu.domain.MenuDO;

/**
 * @author wangxd
 * @date 2020-12-02 18:49
 */
@Data
@YuQuery(domain = MenuDO.class)
public class MenuQuery {
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String name;
}
