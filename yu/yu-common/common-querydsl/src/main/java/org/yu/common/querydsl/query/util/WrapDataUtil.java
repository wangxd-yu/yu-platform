package org.yu.common.querydsl.query.util;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.data.domain.Pageable;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.api.MultiDataTypeEnum;
import org.yu.common.querydsl.api.TreeNode;
import org.yu.common.querydsl.util.TreeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据包装工具
 *
 * @author wangxd
 * @date 2020-10-10
 */
public class WrapDataUtil {

    public static Map<String, Object> toPage(Object object, long total) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("data", object);
        map.put("total", total);
        return map;
    }

    public static <T> MultiDataResult<T> toList(AbstractJPAQuery<T, ?> jpaQuery) {
        QueryResults<T> queryResults = jpaQuery.fetchResults();
        return MultiDataResult.<T>builder()
                .type(MultiDataTypeEnum.LIST)
                .data(queryResults.getResults())
                .total(queryResults.getTotal())
                .success(true)
                .build();
    }

    public static <T> MultiDataResult<T> toPage(AbstractJPAQuery<T, ?> jpaQuery, Pageable pageable) {
        QueryResults<T> queryResults = jpaQuery
                .limit(pageable.getPageSize())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .fetchResults();
        return MultiDataResult.<T>builder()
                .type(MultiDataTypeEnum.PAGE)
                .data(queryResults.getResults())
                .current(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .total(queryResults.getTotal())
                .success(true)
                .build();
    }

    public static <T extends TreeNode<T>> MultiDataResult<T> toTree(AbstractJPAQuery<T, ?> jpaQuery) {
        QueryResults<T> queryResults = jpaQuery.fetchResults();
        return MultiDataResult.<T>builder()
                .type(MultiDataTypeEnum.TREE)
                .data(TreeUtil.buildTree(queryResults.getResults()))
                .total(queryResults.getTotal())
                .success(true)
                .build();
    }
}
