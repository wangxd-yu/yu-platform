package org.yu.common.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangxd
 * @date 2020-11-26
 */
public interface JpaBaseRepository<DO, ID> extends JpaRepository<DO, ID> {
}
