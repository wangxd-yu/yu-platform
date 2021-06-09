package org.yu.serve.system.module.dict.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.dict.domain.DictDetailDO;
import org.yu.serve.system.module.dict.domain.QDictDetailDO;
import org.yu.serve.system.module.dict.repository.DictDetailRepository;
import org.yu.serve.system.module.dict.service.DictDetailService;

/**
 * @author wangxd
 * @date 2020-11-27 15:07
 */
@Service
public class DictDetailServiceImpl extends DslBaseServiceImpl<DictDetailRepository, DictDetailDO, Long> implements DictDetailService {

    QDictDetailDO qDictDetailDO = QDictDetailDO.dictDetailDO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDictId(Long dictId) {
        getJPAQueryFactory().delete(qDictDetailDO)
                .where(qDictDetailDO.dictId.eq(dictId))
                .execute();
    }
}
