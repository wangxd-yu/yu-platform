package org.yu.generate.modules.view.domain;

import lombok.Data;

/**
 * @author wangxd
 * @date 2023-04-18 22:58
 */
@Data
public class GenViewModuleDO {

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块类型：CRUD、TABLE、FORM、DATA
     */
    private String type;

    /**
     * 模块类型=CRUD 对应的属性
     */
    private GenViewCrudDO genViewCrudDO;

    /**
     * 表格 属性
     */
    private GenViewTableDO genViewTableDO;
}
