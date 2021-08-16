package org.yu.common.querydsl.service;

import org.springframework.data.domain.Pageable;
import org.yu.common.jpa.service.BaseService;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.api.MultiDataTypeEnum;

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

    <Q> MultiDataResult<DO> query(Q query, Pageable pageable);

    <Q, DTO> MultiDataResult<DTO> queryDTO(Q query, Pageable pageable, Class<DTO> clazz, MultiDataTypeEnum typeEnum);

    default <Q, DTO> MultiDataResult<DTO> queryDTO(Q query, Pageable pageable, Class<DTO> clazz) {
        return queryDTO(query, pageable, clazz, null);
    }
}
