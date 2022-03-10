package org.yu.common.core.util;

import org.springframework.stereotype.Component;
import org.yu.common.core.service.DataScopeService;

import java.util.Collections;
import java.util.Set;

/**
 * @author wangxd
 * @date 2022-03-10 18:10
 */
@Component
public class DataScopeUtil {
    private static DataScopeUtil dataScopeUtil;

    private final DataScopeService dataScopeService;

    public DataScopeUtil(DataScopeService dataScopeService) {
        dataScopeUtil = this;
        this.dataScopeService = dataScopeService;
    }

    /**
     * @param deptId 当前节点
     * @return 子节点 列表
     */
    public static Set<String> listSonDeptIds(String deptId) {
        return dataScopeUtil.dataScopeService.listSonDeptIds(deptId);
    }

    /**
     * @param deptId 当前节点
     * @return 当前节点 + 子节点 列表
     */
    public static Set<String> listCurrentAndSonDeptIds(String deptId) {
        Set<String> ids = DataScopeUtil.listSonDeptIds(deptId);
        if (ids != null) {
            ids.add(deptId);
        } else {
            ids = Collections.singleton(deptId);
        }
        return ids;
    }
}
