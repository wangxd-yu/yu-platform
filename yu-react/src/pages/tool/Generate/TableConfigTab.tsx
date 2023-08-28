/**
 * 收集 前端表格查询条件，对应后端属性：GenViewTableColumnDO 
 */
import type { ProColumns } from "@ant-design/pro-table";
import ConfigTab from "./ConfigTab";
import type { ApiDomainField, ViewTableQuery } from "./data";
import { Switch } from "antd";

interface TableQueryTabProps {
    transferDataSource: ApiDomainField[];
    onValuesChange: (values: any) => void;
}

const columns: ProColumns<ViewTableQuery>[] = [
    {
        title: '字段名',
        dataIndex: 'dataIndex',
        formItemProps: {
            rules: [
                {
                    required: true,
                    whitespace: false,
                    message: '此项是必填项',
                }
            ],
        },
    },
    {
        title: '标题',
        dataIndex: 'title',
        formItemProps: {
            rules: [
                {
                    required: true,
                    whitespace: false,
                    message: '此项是必填项',
                }
            ],
        },
    },
    {
        title: '提示信息',
        dataIndex: 'tooltip'
    },
    {
        title: '是否查询',
        dataIndex: 'search',
        renderFormItem: () => {
            return <Switch />;
        },
    },
    {
        title: '查询条件',
        dataIndex: 'operator',
        valueType: 'select',
        editable: (value, record) => {
            return record.search ? true : false;
        },
        valueEnum: {
            EQUAL: { text: '相等' },
            NOT_EQUAL: { text: '不相等' },
            GREATER_THAN: { text: '大于' },
            GREATER_OR_EQUAL: { text: '大于等于' },
            LESS_THAN: { text: '小于' },
            LESS_OR_EQUAL: { text: '小于等于' },
            INNER_LIKE: { text: 'like' },
            NOT_INNER_LIKE: { text: 'not like' },
            START_WITH: { text: 'start with' },
            END_WITH: { text: 'end with' },
            IN: { text: 'in' },
        },
    },
    {
        title: '搜索表单的默认值',
        dataIndex: 'initialValue'
    },
    {
        title: '是否缩略',
        dataIndex: 'ellipsis',
        renderFormItem: () => {
            return <Switch />;
        },
    },
    {
        title: '是否拷贝',
        dataIndex: 'copyable',
        renderFormItem: () => {
            return <Switch />;
        },
    },
    {
        title: '组件',
        key: 'component',
        dataIndex: 'component',
        valueType: 'select',
        valueEnum: {
            String: { text: 'String' },
            Integer: { text: 'Integer' },
            Datetime: { text: 'Datetime' },
        },
    },

    {
        title: '排序',
        width: 50,
        dataIndex: 'sort',
        valueType: 'digit',
        editable: false
    },
    {
        title: '操作',
        valueType: 'option',
    }
];

const TableConfigTab: React.FC<TableQueryTabProps> = (props) => {
    return <>
        <ConfigTab<ViewTableQuery>
            transferDataSource={props.transferDataSource}
            columns={columns}
            mapApiDomainFieldToT={(item) => {
                return {
                    id: item.id,
                    field: item.name,
                    title: item.comment,
                    length: item.length,
                    sort: item.sort,
                    search: false
                } as ViewTableQuery;
            }}
            onValuesChange={props.onValuesChange} />
    </>
}

export default TableConfigTab;