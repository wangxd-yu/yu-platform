package org.yu.admin.archive.module.intro.service;

import org.springframework.stereotype.Service;
import org.yu.admin.archive.module.intro.domain.IntroDO;
import org.yu.admin.archive.module.intro.domain.IntroSnapshotDO;
import org.yu.admin.archive.module.intro.domain.QIntroDO;
import org.yu.admin.archive.module.intro.domain.QIntroSnapshotDO;
import org.yu.admin.archive.module.intro.repository.IntroRepository;
import org.yu.admin.archive.module.intro.repository.IntroSnapshotRepository;
import org.yu.common.querydsl.service.DslBaseServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxd
 * @date 2024-12-21 11:36
 */
@Service
public class IntroServiceImpl extends DslBaseServiceImpl<IntroRepository, IntroDO, String> implements IntroService {

    final QIntroDO qIntroDO = QIntroDO.introDO;
    final QIntroSnapshotDO qIntroSnapshotDO = QIntroSnapshotDO.introSnapshotDO;

    @Resource
    private IntroSnapshotRepository introSnapshotRepository;

    @Override
    public IntroDO findByDatabaseId(String databaseId) {
        return super.baseRepository.findOne(qIntroDO.databaseId.eq(databaseId)).orElse(null);
    }

    @Override
    public void saveOrUpdate(IntroDO introDO) {
        IntroDO intro = null;
        if (introDO.getId() != null) {
            super.baseRepository.findById(introDO.getId()).orElse(null);
        }
        if (intro == null) {
            super.save(introDO);
        } else {
            super.delete(intro.getId());
            super.save(introDO);
        }
    }

    @Override
    public List<IntroDO> findListByDatabaseId(String databaseId) {
        return (List<IntroDO>) super.baseRepository.findAll(qIntroDO.databaseId.eq(databaseId));
    }

    @Override
    public IntroSnapshotDO findSnapshotByDatabaseId(String databaseId) {
        return introSnapshotRepository.findOne(qIntroSnapshotDO.databaseId.eq(databaseId)).orElse(null);
    }

    @Override
    public void saveOrUpdate(IntroSnapshotDO introSnapshotDO) {
        IntroSnapshotDO introSnapshot = findSnapshotByDatabaseId(introSnapshotDO.getDatabaseId());
        if (introSnapshot == null) {
            introSnapshotRepository.save(introSnapshotDO);
        } else {
            introSnapshotRepository.deleteById(introSnapshot.getId());
            introSnapshotRepository.save(introSnapshotDO);
        }
    }

    @Override
    public void deleteByDatabaseId(String databaseId) {
        super.baseRepository.deleteById(databaseId);
    }
}
