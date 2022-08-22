package org.yu.serve.system.module.virtualdept.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.dept.dto.DeptTreeDTO;
import org.yu.serve.system.module.virtualdept.domain.VirtualDeptDO;
import org.yu.serve.system.module.virtualdept.query.VirtualDeptQuery;

import java.util.List;

/**
 * @author wangxd
 */
public interface VirtualDeptService extends DslBaseService<VirtualDeptDO, String> {
    <T extends DeptTreeDTO> List<T> queryAll(VirtualDeptQuery query, Class clazz);

    /**
     * 根据 virtualDeptId 获取关联 dept 的ids
     *
     * @param virtualDeptId
     * @return
     */
    List<String> getMenuEndpoints(String virtualDeptId);

    /**
     * 新增、更新 虚拟部门与实际部门ids的映射关系
     * @param id        虚拟部门id
     * @param deptIds   实际部门ids
     * @return
     */
    Object saveVirtualDeptDeptIds(String id, String[] deptIds);
}
