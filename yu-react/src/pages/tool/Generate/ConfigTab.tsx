import React, { useRef, useState } from 'react';
import { Button, Modal } from 'antd';
import TableTransfer from './TableTransfer';
import type { ProFormInstance } from '@ant-design/pro-form';
import ProForm from '@ant-design/pro-form';
import { FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns } from '@ant-design/pro-table';
import { EditableProTable } from '@ant-design/pro-table';
import lodash from 'lodash'

import type { ApiDomainField } from './data';

// 要求包含 sort 属性的泛型类型
interface Sortable {
    id: string;
    sort: number;
}

interface ConfigTabProps<T extends Sortable> {
    onValuesChange: (values: any) => void;
    children?: React.ReactNode;
    transferDataSource: ApiDomainField[];
    columns: ProColumns<T>[];
    mapApiDomainFieldToT: (apiDomainField: ApiDomainField) => T;
}

const leftColumns = [
    {
        title: '字段名称',
        dataIndex: 'name',
    },
    {
        title: '字段注释',
        dataIndex: 'comment',
    },
];

const rightColumns = [
    {
        title: '字段名称',
        dataIndex: 'name',
    },
    {
        title: '字段注释',
        dataIndex: 'comment',
    },
];

//const ConfigTab: React.FC<ConfigTabProps> = (props) => {
const ConfigTab = <T extends Sortable>(props: ConfigTabProps<T>) => {
    const { transferDataSource } = props;
    const formRef = useRef<ProFormInstance>();
    const [formDataSource, setFormDataSource] = useState<T[]>();
    const [editableKeys, setEditableRowKeys] = useState<React.Key[]>();

    const [isModalOpen, setIsModalOpen] = useState(false);

    const [targetKeys, setTargetKeys] = useState<string[]>();

    const onFormTransferChange = (nextTargetKeys: string[]) => {
        setTargetKeys(nextTargetKeys);
    };

    const showModal = () => {
        setIsModalOpen(true);
    };

    /**
     * 上移 数据库属性 
     * @param row 
     * @returns 
     */
    const moveUp = (row: T) => {
        if (formDataSource) {
            // 如果是第一个
            if (row.sort === lodash.min(formDataSource.map(item => item.sort))) {
                return
            } else {
                const upRow = formDataSource.find(item => item.sort === row.sort - 1)
                const currentRow = formDataSource.find(item => item.id === row.id)
                if (upRow) {
                    upRow.sort++
                }
                if (currentRow) {
                    currentRow.sort--
                }
                setFormDataSource(formDataSource.toSorted((a, b) => { return a.sort - b.sort }));
            }
        }
    }

    /**
     * 下移 数据库属性 
     * @param row 
     * @returns 
     */
    const moveDown = (row: T) => {
        if (formDataSource) {
            // 如果是最后一个
            if (row.sort === lodash.max(formDataSource.map(item => item.sort))) {
                return
            } else {
                const upRow = formDataSource.find(item => item.sort === row.sort + 1);
                const currentRow = formDataSource.find(item => item.id === row.id);
                if (upRow) {
                    upRow.sort--;
                }
                if (currentRow) {
                    currentRow.sort++;
                }
                setFormDataSource(formDataSource.toSorted((a, b) => a.sort - b.sort));
            }
        }
    }

    const handleOk = () => {
        const formColumns = transferDataSource.filter(item => targetKeys?.includes(item.key as string)).map(item => {
            return props.mapApiDomainFieldToT(item)
            /*return {
                id: item.id,
                field: item.name,
                title: item.comment,
                length: item.length,
                sort: item.sort
            }*/
        })
        setFormDataSource(formColumns)
        setEditableRowKeys(targetKeys)
        setIsModalOpen(false);
    };

    const handleCancel = () => {
        setIsModalOpen(false);
    };
    return (
        <>
            <ProForm
                grid
                onValuesChange={(changeValues) => console.log("changeValues", changeValues)}
                formRef={formRef}
                submitter={{
                    render: (_, dom) => <FooterToolbar style={{ width: '100%' }}>{dom}</FooterToolbar>,
                }}
                onFinish={async (values) => console.log(values)}
            >
                {props.children}

                <ProForm.Item
                    name="transferDataSource"
                    initialValue={[]}
                    trigger="onValuesChange"
                >
                    <EditableProTable<T>
                        ghost
                        rowKey="id"
                        /*toolBarRender={false}*/
                        value={formDataSource}

                        // 关闭默认的新建按钮
                        recordCreatorProps={false}
                        columns={props.columns}
                        toolBarRender={() => {
                            return [
                                <Button
                                    type="primary"
                                    key="save"
                                    onClick={showModal}
                                >
                                    属性维护
                                </Button>,
                            ];
                        }}
                        editable={{
                            type: 'multiple',
                            editableKeys,
                            actionRender: (row, config, defaultDom) => {
                                return [
                                    <a
                                        style={
                                            row.sort === lodash.min(formDataSource?.map(item => item.sort))
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
                                            row.sort === lodash.max(formDataSource?.map(item => item.sort))
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
                                setFormDataSource(recordList);
                                console.log("formRef.current", formRef.current)
                                setTargetKeys(recordList.map(item => item.id))
                            },
                            onChange: setEditableRowKeys,
                        }}
                    />
                </ProForm.Item>
            </ProForm>

            <Modal title="Basic Modal" open={isModalOpen} onOk={handleOk} onCancel={handleCancel} width={1000}>
                <TableTransfer
                    dataSource={transferDataSource.map((item) => {
                        item.key = item.id;
                        return item;
                    })}
                    leftColumns={leftColumns}
                    rightColumns={rightColumns}
                    targetKeys={targetKeys}
                    onChange={onFormTransferChange}
                />
            </Modal>
        </>
    );
};

export default ConfigTab;