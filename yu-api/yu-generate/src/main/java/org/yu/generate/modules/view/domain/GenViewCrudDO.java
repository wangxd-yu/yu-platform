package org.yu.generate.modules.view.domain;

import lombok.Data;
import org.yu.generate.modules.api.module.domain.GenApiModuleDO;

/**
 * 模块类型：CRUD(增删改查)
 *
 * @author wangxd
 * @date 2023-10-01 23:50
 */
@Data
public class GenViewCrudDO {
    /**
     * 对应的后端模块信息
     */
    private GenApiModuleDO genApiModuleDO;

    /**
     * 表单信息
     */
    private GenViewFormDO genViewFormDO;

    /**
     * 表格信息（包括：展示列；查询条件）
     */
    private GenViewTableDO genViewTableDO;
}
