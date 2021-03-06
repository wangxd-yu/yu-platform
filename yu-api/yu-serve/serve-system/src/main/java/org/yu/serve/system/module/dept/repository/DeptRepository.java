package org.yu.serve.system.module.dept.repository;

import org.yu.common.querydsl.repository.DslBaseRepository;
import org.yu.serve.system.module.dept.domain.DeptDO;

/**
 * @author wangxd
 * @date 2020-11-30 14:57
 */
public interface DeptRepository extends DslBaseRepository<DeptDO, String> {
    /**
     * 判断是否存在子节点
     */
    int countByPid(String pid);
}
