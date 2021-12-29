package org.yu.serve.system.module.dict.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.dict.domain.DictDetailDO;

/**
 * @author wangxd
 * @date 2020-11-27 15:05
 */
public interface DictDetailService extends DslBaseService<DictDetailDO, String> {
    void deleteByDictId(String dictId);
}
