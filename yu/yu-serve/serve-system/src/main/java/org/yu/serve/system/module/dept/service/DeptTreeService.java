package org.yu.serve.system.module.dept.service;

import org.yu.serve.system.module.dept.dto.DeptTreeDTO;

import java.util.List;
import java.util.Map;

/**
 * @author wangxd
 * @date 2020-11-30 19:48
 */
public interface DeptTreeService {

    void initTree();

    <T extends DeptTreeDTO> Map<String, Object> buildTree(List<T> deptDTOS);

    /**
     * 构造前端页面显示的机构树
     */
    <T extends DeptTreeDTO> List<T> buildShowTree(List<T> deptDTOS);

    /**
     * 查询前端页面显示的机构树
     */
    List<DeptTreeDTO> getShowTreeByDeptNo(String deptNo);
}
