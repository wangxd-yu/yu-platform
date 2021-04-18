package org.yu.common.jpa.service;

/**
 * @author wangxd
 * @date 2020-11-09 09:30
 */
public interface BaseService<DO, ID> {

    DO save(DO domain);

    void update(DO domain);

    void delete(ID id);

    DO getById(ID id);
}
