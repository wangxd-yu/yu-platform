package com.yu.common.querydsl.query.util;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.sql.JPASQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页工具
 *
 * @author yu
 * @date 2018-12-10
 */
public class PageUtil extends cn.hutool.core.util.PageUtil {

    /**
     * List 分页
     *
     * @param page
     * @param size
     * @param list
     * @return
     */
    public static List toPage(int page, int size, List list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;

        if (fromIndex > list.size()) {
            return new ArrayList();
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     *
     * @param page
     * @return
     */
    public static Map toPage(Page page) {
        Map map = new HashMap();
        map.put("content", page.getContent());
        map.put("totalElements", page.getTotalElements());
        return map;
    }

    /**
     * @param object
     * @param totalElements
     * @return
     */
    public static Map toPage(Object object, Object totalElements) {
        Map map = new HashMap();
        map.put("content", object);
        map.put("totalElements", totalElements);
        return map;
    }

    public static Map<String, Object> toPage(JPAQuery<?> jpaQuery, Pageable pageable) {
        //TODO 获取泛型类型并实现分页
        Map map = new HashMap();
        QueryResults<?> queryResults = jpaQuery
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .fetchResults();
        map.put("totalElements", queryResults.getTotal());
        map.put("content", queryResults.getResults());
        return map;
    }

    public static Map<String, Object> toPage(JPASQLQuery<?> jpaSqlQuery, Pageable pageable) {
        //TODO 获取泛型类型并实现分页
        Map map = new HashMap();
        QueryResults<?> queryResults = jpaSqlQuery
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .fetchResults();
        map.put("totalElements", queryResults.getTotal());
        map.put("content", queryResults.getResults());
        return map;
    }
}
