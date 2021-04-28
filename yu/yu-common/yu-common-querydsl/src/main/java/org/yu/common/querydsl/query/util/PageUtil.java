package org.yu.common.querydsl.query.util;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页工具
 *
 * @author wangxd
 * @date 2020-10-10
 */
public class PageUtil {

    public static Map<String, Object> toPage(Object object, long total) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("items", object);
        map.put("total", total);
        return map;
    }

    public static Map<String, Object> toPage(AbstractJPAQuery<?, ?> jpaQuery, Pageable pageable) {
        HashMap<String, Object> map = new HashMap<>(2);
        QueryResults<?> queryResults = jpaQuery
                .limit(pageable.getPageSize())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .fetchResults();
        map.put("total", queryResults.getTotal());
        map.put("items", queryResults.getResults());
        return map;
    }
}
