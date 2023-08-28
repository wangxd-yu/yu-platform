export type OperatorEnum = 'EQUAL' | 'NOT_EQUAL' | 'GREATER_THAN' | 'GREATER_OR_EQUAL'
    | 'LESS_THAN' | 'LESS_OR_EQUAL' | 'INNER_LIKE' | 'NOT_INNER_LIKE' | 'START_WITH' | 'END_WITH' | 'IN' | undefined;

/**
* 数据库字段
*/
export type ApiDomainField = {
    key?: string;   //在穿梭框中做 key使用，不映射数据库
    id: string;
    name?: string;
    type?: string;
    length?: number;
    decimal?: number;
    comment?: string;
    sort: number;
    ifNullable?: string;
};

/**
 * 前端 表格展示列
 */
export type ViewTableColumn = {
    dataIndex: string;  //与实体映射的key
    title: string;  //标题
    tooltip?: string;    //提示信息
    colSize?: number;    //每个表单占据的格子大小
    initialValue?: string;  //
    order?: number;    // Form 的排序
}

/**
 * 前端 查询条件展示列
 */
export type ViewTableQuery = {
    id: string;
    sort: number;
    search?: boolean;
    field: string;  //字段名
    title: string;  //标签名
    component?: string;  //组件
    operator?: OperatorEnum;   //操作
}

/**
 * 前端表单字段属性
 */
export type ViewFormColumn = {
    id: string;
    field: string;
    title: string;
    length?: number;
    sort: number;
}