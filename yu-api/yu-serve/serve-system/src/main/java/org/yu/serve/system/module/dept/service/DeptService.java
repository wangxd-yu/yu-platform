package org.yu.serve.system.module.dept.service;

import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.dto.DeptRoleDTO;
import org.yu.serve.system.module.dept.dto.DeptTreeDTO;
import org.yu.serve.system.module.dept.query.DeptQuery;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-30 14:35
 */
public interface DeptService extends DslBaseService<DeptDO, String> {

    <T extends DeptTreeDTO> List<T> queryAll(DeptQuery query, Class clazz);

    void changeEnabled(String id, boolean enabled);

    MultiDataResult<DeptRoleDTO> getDeptRoles(String deptId);

    void saveDeptRoles(String deptId, String[] roleIds);

    void deleteDeptRoles(String deptId, String[] roleIds);

    /**
     * 部门移入
     * @param deptId    目标部门id
     * @param sourceIds 移入部门ids
     */
    void moveIn(String deptId, String[] sourceIds);
}
