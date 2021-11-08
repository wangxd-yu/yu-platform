package org.yu.common.web.util;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.yu.common.core.context.YuContextHolder;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author wangxd
 * @date 2021-10-29 8:45
 */
public class QueryDslUtil {

    private static QueryDslUtil queryDslUtil;

    @PersistenceContext
    protected EntityManager entityManager;

    @PostConstruct
    private void init() {
        queryDslUtil = this;
    }

    public static JPAQueryFactory getJPAQueryFactory() {
        Object jpaQueryFactoryObject = YuContextHolder.getYuContext().getJpaQueryFactory();
        if (jpaQueryFactoryObject != null) {
            return (JPAQueryFactory) jpaQueryFactoryObject;
        } else {
            JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(queryDslUtil.entityManager);
            YuContextHolder.getYuContext().setJpaQueryFactory(jpaQueryFactory);
            return jpaQueryFactory;
        }
    }
}
