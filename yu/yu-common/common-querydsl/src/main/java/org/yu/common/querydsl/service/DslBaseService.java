package org.yu.common.querydsl.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.api.MultiDataTypeEnum;
import org.yu.common.querydsl.domain.DslBaseDO;

/**
 * @author wangxd
 * @date 2020-11-30
 */
public interface DslBaseService<DO extends DslBaseDO, ID> {

    DO save(DO domain);

    void update(DO domain);

    void delete(ID id);

    DO getById(ID id);

    <Q> MultiDataResult<DO> query(Q query, Pageable pageable);

    <Q, DTO> MultiDataResult<DTO> queryDTO(Q query, Pageable pageable, Class<DTO> clazz, MultiDataTypeEnum typeEnum);

    default <Q, DTO> MultiDataResult<DTO> queryDTO(Q query, Pageable pageable, Class<DTO> clazz) {
        return queryDTO(query, pageable, clazz, null);
    }

    <DTO> MultiDataResult<DTO> getMultiDataResult(JPAQuery<DTO> jpaQuery, Pageable pageable,MultiDataTypeEnum typeEnum);

    default <DTO> MultiDataResult<DTO> getMultiDataResult(JPAQuery<DTO> jpaQuery, Pageable pageable) {
        return getMultiDataResult(jpaQuery, pageable,null);
    }
}
