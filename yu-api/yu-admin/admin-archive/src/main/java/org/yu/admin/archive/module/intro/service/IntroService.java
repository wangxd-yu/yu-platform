package org.yu.admin.archive.module.intro.service;

import org.yu.admin.archive.module.intro.domain.IntroDO;
import org.yu.admin.archive.module.intro.domain.IntroSnapshotDO;
import org.yu.common.querydsl.service.DslBaseService;

import java.util.List;

/**
 * @author wangxd
 * @date 2024-12-21 11:27
 */
public interface IntroService extends DslBaseService<IntroDO, String> {

    IntroDO findByDatabaseId(String databaseId);

    void saveOrUpdate(IntroDO introDO);

    List<IntroDO> findListByDatabaseId(String databaseId);

    IntroSnapshotDO findSnapshotByDatabaseId(String databaseId);

    void saveOrUpdate(IntroSnapshotDO introSnapshotDO);

    void deleteByDatabaseId(String databaseId);
}
