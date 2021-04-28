package org.yu.common.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author wangxd
 * @date 2020-11-26
 */
public interface DslBaseRepository<DO,ID> extends JpaRepository<DO, ID>, QuerydslPredicateExecutor<DO> {
}
