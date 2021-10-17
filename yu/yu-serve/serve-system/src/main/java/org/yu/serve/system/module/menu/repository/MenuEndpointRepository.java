package org.yu.serve.system.module.menu.repository;

import org.yu.common.querydsl.repository.DslBaseRepository;
import org.yu.serve.system.module.menu.domain.MenuEndpointDO;
import org.yu.serve.system.module.menu.domain.MenuEndpointKeys;

/**
 * @author wangxd
 * @date 2021-10-17
 */
public interface MenuEndpointRepository extends DslBaseRepository<MenuEndpointDO, MenuEndpointKeys> {
}
