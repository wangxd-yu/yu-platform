package org.yu.admin.archive.module.archive.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.yu.common.querydsl.repository.DslBaseRepository;

import java.util.Optional;

/**
 * @author wangxd
 * @date 2024-12-23 10:20
 */
@NoRepositoryBean
public interface BaseArchiveRepository<DO, ID> extends DslBaseRepository<DO, ID> {
    Optional<DO> findByTitleIs(String title);
}
