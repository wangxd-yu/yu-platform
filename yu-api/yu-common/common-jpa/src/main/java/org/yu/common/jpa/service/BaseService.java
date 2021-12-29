package org.yu.common.jpa.service;

import org.yu.common.jpa.domain.JpaBaseDO;

/**
 * @author wangxd
 * @date 2020-11-09
 */
public interface BaseService<DO extends JpaBaseDO, ID> {

    DO save(DO domain);

    void update(DO domain);

    void delete(ID id);

    DO getById(ID id);
}
