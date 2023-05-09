package org.yu.generate.modules.view.domain;

import lombok.Data;

/**
 * 前端 表单展示列
 *
 * @author wangxd
 * @date 2023-04-15 23:32
 */
@Data
public class GenViewTableColumnDO {
    /**
     * 与实体映射的key
     */
    private String dataIndex;

    /**
     * 标题
     */
    private String title;

    /**
     * 提示信息
     */
    private String tooltip;

    /**
     * 每个表单占据的格子大小
     */
    private Integer colSize;

    /**
     * 【搜索】搜索表单的默认值
     */
    private String initialValue;

    /**
     * 是否缩略,扩展属性：showTitle?: boolean;
     */
    private Boolean ellipsis;

    /**
     * 是否拷贝
     */
    private Boolean copyable;

    /**
     * 【搜索】在查询表单中隐藏 ,扩展属性：转化值的key, 一般用于事件区间的转化（Transform: (value: any) => ({ startTime: value[0], endTime: value[1] }),）
     */
    private Boolean search;

    /**
     * 在 table 中隐藏
     */
    private Boolean hideInTable;

    /**
     * 在新建表单中删除
     */
    private Boolean hideInForm;

    /**
     * 不在配置工具中显示
     */
    private Boolean hideInSetting;

    /**
     * 表头的筛选菜单项
     */
    private Boolean filters;

    /**
     * 筛选的函数，设置为 false 会关闭自带的本地筛选
     */
    private Boolean onFilter;

    /**
     * Form 的排序
     */
    private Integer order;

    /**
     * 可编辑表格是否可编辑
     */
    private Boolean editable;

    /**
     * 只读
     */
    private Boolean readonly;

    /**
     * 列设置的 disabled
     */
    private Boolean disable;

}
