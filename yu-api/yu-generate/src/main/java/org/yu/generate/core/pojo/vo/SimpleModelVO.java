package org.yu.generate.core.pojo.vo;

import lombok.Data;
import org.yu.generate.modules.api.domain.domain.GenApiDomainDO;
import org.yu.generate.modules.view.domain.GenViewFormColumnDO;
import org.yu.generate.modules.view.domain.GenViewTableColumnDO;
import org.yu.generate.modules.view.domain.GenViewTableQueryDO;

/**
 * 简单 代码生成模型VO
 *
 * @author wangxd
 * @date 2023-04-15 23:15
 */
@Data
public class SimpleModelVO {

    /**
     * 作者
     */
    private String author;

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 数据库映射实体
     */
    private GenApiDomainDO genApiDomainDO;

    /**
     * 表格 查询条件
     */
    private GenViewTableQueryDO genViewTableQueryDO;

    /**
     * 表格 查询表格列
     */
    private GenViewTableColumnDO genViewTableColumnDO;

    /**
     * 表单 表单项
     */
    private GenViewFormColumnDO genViewFormColumnDO;
}
