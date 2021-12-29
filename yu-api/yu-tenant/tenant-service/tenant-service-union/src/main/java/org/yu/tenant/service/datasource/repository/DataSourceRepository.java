package org.yu.tenant.service.datasource.repository;

import org.yu.common.querydsl.repository.DslBaseRepository;
import org.yu.tenant.service.api.datasource.domain.DataSourceDO;

/**
 * DataSourceRepository
 *
 * @author wangxd
 * @date 2021-04-06 11:10
 */
public interface DataSourceRepository extends DslBaseRepository<DataSourceDO, Long> {
}
