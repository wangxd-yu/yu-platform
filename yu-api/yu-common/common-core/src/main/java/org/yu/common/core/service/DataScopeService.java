package org.yu.common.core.service;

import java.util.Set;

/**
 * @author wangxd
 * @date 2022-03-10 18:09
 */
public interface DataScopeService {

    /**
     * 获取 子部门id列表
     * @param deptId 当前部门id
     * @return 子部门id列表
     */
    Set<String> listSonDeptIds(String deptId);

    /**
     * 获取 父部门id列表
     * @param deptId 当前部门id
     * @return 父部门id列表
     */
    Set<String> listParentDeptIds(String deptId);
}
