package org.yu.generate.modules.view.domain;

import lombok.Data;

import java.util.List;

/**
 * @author wangxd
 * @date 2023-04-26 22:59
 */
@Data
public class GenViewTable {

    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表格列，包含：展示列、查询列、表头筛选菜单
     */
    private List<GenViewTableColumnDO> tableColumns;

    //private GenViewTableOptionDO tableOptionDO;
}
