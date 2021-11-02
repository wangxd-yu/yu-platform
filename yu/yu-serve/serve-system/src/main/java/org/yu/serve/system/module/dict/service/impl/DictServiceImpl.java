package org.yu.serve.system.module.dict.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.common.querydsl.valid.annotation.YuDependValid;
import org.yu.serve.system.module.dict.domain.DictDO;
import org.yu.serve.system.module.dict.domain.DictDetailDO;
import org.yu.serve.system.module.dict.repository.DictRepository;
import org.yu.serve.system.module.dict.service.DictDetailService;
import org.yu.serve.system.module.dict.service.DictService;

import javax.validation.Valid;

/**
 * @author wangxd
 * @date 2020-11-27 14:32
 */
@Service
public class DictServiceImpl extends DslBaseServiceImpl<DictRepository, DictDO, String> implements DictService {

    private final DictDetailService dictDetailService;

    public DictServiceImpl(DictDetailService dictDetailService) {
        this.dictDetailService = dictDetailService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        super.delete(id);
        dictDetailService.deleteByDictId(id);
    }
}
