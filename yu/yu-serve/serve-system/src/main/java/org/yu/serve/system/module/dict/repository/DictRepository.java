package org.yu.serve.system.module.dict.repository;

import org.yu.serve.system.module.dict.domain.DictDO;
import org.yu.common.querydsl.repository.DslBaseRepository;

/**
 * @author wangxd
 * @date 2020-11-27 14:30
 */
public interface DictRepository extends DslBaseRepository<DictDO, String> {
}
