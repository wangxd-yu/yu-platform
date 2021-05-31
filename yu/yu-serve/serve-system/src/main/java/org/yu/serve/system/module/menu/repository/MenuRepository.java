package org.yu.serve.system.module.menu.repository;

import org.yu.common.querydsl.repository.DslBaseRepository;
import org.yu.serve.system.module.menu.domain.MenuDO;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-26 14:38
 */
public interface MenuRepository extends DslBaseRepository<MenuDO, Long> {
    List<MenuDO> findByPid(long pid);
}
