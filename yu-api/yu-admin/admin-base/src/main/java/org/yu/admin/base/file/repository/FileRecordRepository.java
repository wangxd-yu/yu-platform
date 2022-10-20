package org.yu.admin.base.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.yu.serve.file.domain.FileRecordDO;

public interface FileRecordRepository extends JpaRepository<FileRecordDO, Long>, QuerydslPredicateExecutor<FileRecordDO> {
}
