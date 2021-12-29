package org.yu.tenant.service.api.datasource.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.tenant.service.api.datasource.domain.DataSourceDO;

/**
 * DataSource 查询条件
 *
 * @author wangxd
 * @date 2021-04-07 17:08
 */
@Data
@YuQuery(domain = DataSourceDO.class)
public class DataSourceQuery {
    /**
     * 组id
     */
    private Long groupId;

    private Boolean enabled = true;
}
