import React, { useEffect, useRef, useState } from 'react';
import 'prismjs/themes/prism.css';
import 'prismjs/plugins/line-numbers/prism-line-numbers';
import 'prismjs/plugins/line-numbers/prism-line-numbers.css'

import 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import type { EditableFormInstance, ProColumns } from '@ant-design/pro-table';
import { EditableProTable } from '@ant-design/pro-table';
import type { ApiDomainField } from './data';

import lodash from 'lodash'
import ProForm, { ProFormText } from '@ant-design/pro-form';
import { FooterToolbar } from '@ant-design/pro-layout';
import { message } from 'antd';

interface DbConfigTabProps {
    dataSource?: ApiDomainField[];
    onValuesChange: (dataSource: ApiDomainField[]) => void;
}

const waitTime = (time: number = 100) => {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve(true);
        }, time);
    });
};
const formLayoutType = 'horizontal'
const formItemLayout =
{
    labelCol: { span: 6 },
    wrapperCol: { span: 18 },
};

const defaultData: ApiDomainField[] = [{
    id: (Date.now()).toString() + "_id",
    sort: 1,
    name: `id`,
    type: 'String',
    length: 20,
    comment: "主键"
}, {
    id: (Date.now()).toString() + "_ct",
    sort: 2,
    name: `create_time`,
    type: 'Datetime',
    length: 3,
    comment: "创建时间"
}, {
    id: (Date.now()).toString() + "_ut",
    sort: 3,
    name: `update_time`,
    type: 'Datetime',
    length: 3,
    comment: "更新时间"
}];


const DbConfigTab: React.FC<DbConfigTabProps> = (prop) => {
    const [dataSource, setDataSource] = useState<ApiDomainField[]>(
        () => defaultData,
    );
    const [editableKeys, setEditableRowKeys] = useState<React.Key[]>(() =>
        defaultData.filter((item, i) => { return i > 1 }).map((item) => item.id),
    );
    const dbEditorFormRef = useRef<EditableFormInstance<any>>();

    useEffect(() => {
        prop.onValuesChange(dataSource)
    }, [dataSource])

    /**
     * 表单校验规则
     */
    const handleValidator = (_rule: any, value: string | undefined) => {
        const isnameDuplicate = dataSource.some((item) => item.name === value);
        if (isnameDuplicate) {
            return Promise.reject('字段名称不能重复');
        }
        return Promise.resolve();
    };

    /**
     * 上移 数据库属性 
     * @param row 
     * @returns 
     */
    const moveUp = (row: ApiDomainField) => {
        // 如果是第一个
        if (row.sort === lodash.min(dataSource.map(item => item.sort))) {
            return
        } else {
            const upRow = dataSource.find(item => item.sort === row.sort - 1)
            const currentRow = dataSource.find(item => item.id === row.id)
            if (upRow) {
                upRow.sort++
            }
            if (currentRow) {
                currentRow.sort--
            }
            setDataSource(dataSource.toSorted((a, b) => { return a.sort - b.sort }));
        }
    }

    /**
     * 下移 数据库属性 
     * @param row 
     * @returns 
     */
    const moveDown = (row: ApiDomainField) => {
        // 如果是最后一个
        if (row.sort === lodash.max(dataSource.map(item => item.sort))) {
            return
        } else {
            const upRow = dataSource.find(item => item.sort === row.sort + 1);
            const currentRow = dataSource.find(item => item.id === row.id);
            if (upRow) {
                upRow.sort--;
            }
            if (currentRow) {
                currentRow.sort++;
            }
            setDataSource(dataSource.toSorted((a, b) => a.sort - b.sort));
        }
    }

    /**
     * 禁用样式
     * @param row 
     * @returns 
     */
    const disableStyle = {
        color: 'rgba(0,0,0,.25)',
        cursor: 'not-allowed',
    }

    const columns: ProColumns<ApiDomainField>[] = [
        {
            title: '字段名称',
            dataIndex: 'name',
            formItemProps: {
                rules: [
                    {
                        required: true,
                        whitespace: false,
                        message: '此项是必填项',
                    }, {
                        validator: handleValidator,
                    },
                ],
            },
        },
        {
            title: '字段类型',
            key: 'type',
            dataIndex: 'type',
            valueType: 'select',
            valueEnum: {
                String: { text: 'String' },
                Integer: { text: 'Integer' },
                Datetime: { text: 'Datetime' },
            },
        },
        {
            title: '字段长度',
            dataIndex: 'length',
            valueType: 'digit',
            width: 100,
        },
        {
            title: '小数点',
            dataIndex: 'commonDecimal',
            valueType: 'digit',
            width: 100,
        },
        {
            title: '排序',
            width: 50,
            dataIndex: 'sort',
            valueType: 'digit',
            editable: false
        },
        {
            title: '不是null',
            dataIndex: 'ifNullable',
            valueType: 'checkbox',
            fieldProps: {
                options: [
                    { label: '', value: 'true' },
                ],
            }
        },
        {
            title: '字段注释',
            dataIndex: 'comment'
        },
        {
            title: '操作',
            valueType: 'option',
            render: (text, record, __, action) => [
                <a
                    key="up"
                    onClick={() => {
                        moveUp(record);
                    }}
                    style={record.sort === lodash.min(dataSource.map(item => item.sort))
                        ? disableStyle
                        : {}
                    }
                >
                    上移
                </a>,
                <a
                    key="down"
                    onClick={() => {
                        moveDown(record);
                    }}
                    style={record.sort === lodash.max(dataSource.map(item => item.sort))
                        ? disableStyle
                        : {}
                    }
                >
                    下移
                </a>,
                <a
                    key="delete"
                    style={record.name === "id"
                        ? disableStyle
                        : {}
                    }
                    onClick={() => {
                        if (dataSource.filter((item) => item.name !== "id")) {
                            setDataSource(dataSource.filter((item) => item.id !== record.id));
                        }
                    }}
                >
                    删除
                </a>
            ],
        },
    ];
    return <>
        <ProForm<{
            name: string;
            company?: string;
            useMode?: string;
        }>
            onValuesChange={(changeValues, values) => console.log("changeValues", changeValues, values)}
            {...formItemLayout}
            layout={formLayoutType}
            submitter={{
                render: (__, dom) => <FooterToolbar style={{ width: '100%' }}>{dom}</FooterToolbar>,
            }}
            onFinish={async (values) => {
                await waitTime(2000);
                console.log(values);
                message.success('提交成功');
            }}
            params={{}}
            request={async () => {
                await waitTime(100);
                return {
                    name: '蚂蚁设计有限公司',
                    useMode: 'chapter',
                };
            }}
            grid
        >
            <ProFormText
                name="name"
                label="签约客户名称"
                tooltip="最长为 24 位"
                placeholder="请输入名称"
                colProps={{ xs: 24, sm: 12, md: 12, lg: 8 }}
            />
            <ProFormText
                name="company"
                label="我方公司名称"
                placeholder="请输入名称"
                colProps={{ xs: 24, sm: 12, md: 12, lg: 8 }}
            />
            <ProFormText
                name={['contract', 'name']}
                label="合同名称"
                placeholder="请输入名称"
                colProps={{ xs: 24, sm: 12, md: 12, lg: 8 }}
            />
            <ProForm.Item
                name="dataSource"
                initialValue={[]}
                trigger="onValuesChange"
                wrapperCol={{span: 24}}
            >
            <EditableProTable<ApiDomainField>
                ghost
                editableFormRef={dbEditorFormRef}
                defaultSize='small'
                columns={columns}
                rowKey="id"
                /*scroll={{
                    x: 960,
                }}*/
                value={dataSource}
                onChange={setDataSource}
                //controlled={true}
                recordCreatorProps={{
                    newRecordType: 'dataSource',
                    record: () => ({
                        id: Date.now().toString(),
                        sort: dataSource.length > 0 ? lodash.max(dataSource.map(item => item.sort))! + 1 : 1, // 使用默认值1
                        decimal: 0
                    }),
                }}
                toolBarRender={() => {
                    return [
                        /*<Button
                          type="primary"
                          key="save"
                          onClick={() => {
                            // dataSource 就是当前数据，可以调用 api 将其保存
                            console.log(dataSource);
                            dbEditorFormRef.current?.validateFields()
                          }}
                        >
                          保存数据
                        </Button>,*/
                    ];
                }}
                editable={{
                    type: 'multiple',
                    editableKeys,
                    actionRender: (row, config, defaultDom) => {
                        return [
                            <a
                                style={
                                    row.sort === lodash.min(dataSource.map(item => item.sort))
                                        ? {
                                            color: 'rgba(0,0,0,.25)',
                                            cursor: 'not-allowed',
                                        }
                                        : {}
                                }
                                key="up"
                                onClick={() => {
                                    moveUp(row)
                                }}
                            >
                                上移
                            </a>,
                            <a
                                style={
                                    row.sort === lodash.max(dataSource.map(item => item.sort))
                                        ? {
                                            color: 'rgba(0,0,0,.25)',
                                            cursor: 'not-allowed',
                                        }
                                        : {}
                                }
                                key="down"
                                onClick={() => {
                                    moveDown(row)
                                }}
                            >
                                下移
                            </a>,
                            //defaultDom.save,
                            defaultDom.delete || defaultDom.cancel,
                        ];
                    },
                    onValuesChange: (record, recordList) => {
                        setDataSource(recordList);
                    },
                    onChange: setEditableRowKeys,
                }}
            />
        </ProForm.Item>
    </ProForm >
    </>
}

export default DbConfigTab;
