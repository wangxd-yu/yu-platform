package org.yu.serve.system.module.dept.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.dept.domain.DeptTypeDO;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-30 14:35
 */
public interface DeptTypeService extends DslBaseService<DeptTypeDO, String> {
    List<DeptTypeDO> findSubTypesByTypeId(String typeId);

    List<DeptTypeDO> findSubTypesByDeptNo(String deptNo);
}
