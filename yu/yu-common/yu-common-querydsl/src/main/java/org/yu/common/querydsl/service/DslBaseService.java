package org.yu.common.querydsl.service;

import org.springframework.data.domain.Pageable;
import org.yu.common.jpa.service.BaseService;

/**
 * @author wangxd
 * @date 2020-11-30
 */
public interface DslBaseService<DO, ID> extends BaseService<DO, ID> {

    @Override
    DO save(DO domain);

    @Override
    void update(DO domain);

    @Override
    void delete(ID id);

    @Override
    DO getById(ID id);

    <Q> Object query(Q query, Pageable pageable);

    <Q, DTO> Object queryDTO(Q query, Pageable pageable, Class<DTO> clazz);
}
