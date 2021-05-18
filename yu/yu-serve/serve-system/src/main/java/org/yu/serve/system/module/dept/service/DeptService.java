package org.yu.serve.system.module.dept.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.dto.DeptTreeDTO;
import org.yu.serve.system.module.dept.query.DeptQuery;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-30 14:35
 */
public interface DeptService extends DslBaseService<DeptDO, Long> {

    DeptDO getByNo(String deptNo);

    <T extends DeptTreeDTO> List<T> queryAll(DeptQuery query, Class clazz);
}
