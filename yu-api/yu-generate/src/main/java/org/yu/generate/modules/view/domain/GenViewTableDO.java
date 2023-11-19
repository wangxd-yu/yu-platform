package org.yu.generate.modules.view.domain;

import lombok.Data;

import java.util.List;

/**
 * @author wangxd
 * @date 2023-04-26 22:59
 */
@Data
public class GenViewTableDO {

    /**
     * 名称
     */
    private String title;
    /**
     * 表格列，包含：展示列、查询列、表头筛选菜单
     */
    private List<GenViewTableColumnDO> columns;

    //private GenViewTableOptionDO tableOptionDO;
}
