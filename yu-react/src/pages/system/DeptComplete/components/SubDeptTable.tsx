import React, { Fragment, useEffect, useRef, useState } from 'react';
import type { FormInstance } from 'antd';
import { Modal } from 'antd';
import { Space } from 'antd';
import { Table } from 'antd';
import { Button } from 'antd';
import { Switch, Popconfirm } from 'antd';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import type { DeptData } from '../data';
import ProTable from '@ant-design/pro-table';
import * as YuCrud from '@/utils/yuCrud';
import { queryDept, deleteDept, disableDept, enableDept, moveDept } from '../service'
import DeptForm from '../../Dept/components/DeptForm';
import { DownOutlined, PlusOutlined } from '@ant-design/icons';
import type { DataNode } from 'antd/lib/tree';
import Tree from 'antd/lib/tree';
import Title from 'antd/lib/typography/Title';

const DeptPage: React.FC<{ dept: DeptData, deptTree: DataNode[], deptChange: () => any }> = (prop: { dept: DeptData, deptTree: DataNode[], deptChange: () => any }) => {
    const [deptFormVisible, setDeptFormVisible] = useState<boolean>(false);
    const [deptCurrentRow, setDeptCurrentRow] = useState<DeptData>();

    // 复选框选中节点ids
    const [selectedDeptIds, setSelectedDeptIds] = useState<string[]>([])
    // 合并部门弹框 是否显示
    const [mergeDeptModelVisible, setMergeDeptModelVisble] = useState<boolean>(false)
    // 树控件选择的 目标部门id
    const [targetDeptId, setTargetDeptId] = useState<string>()

    const deptFormRef = useRef<FormInstance>();
    const deptActionRef = useRef<ActionType>();
    useEffect(() => {

    })

    //递归 处理树节点的 disabled 属性：1）所选节点的父节点；2）所选节点自身；3）所选节点子节点；4）TODO 根据 type判断
    const handleTreeDataRecursion = (data: DataNode[], isChildren: boolean): DataNode[] => {
        if (Array.isArray(data)) {
            data?.forEach((deptData: DataNode) => {
                // 3）所选节点子节点；
                if(isChildren === true) {
                    deptData.disabled = true
                    deptData.children = deptData.children ? handleTreeDataRecursion(deptData.children, true) : []; // 如果还有子集，就再次调用自己
                }
                // 1）所选节点的父节点
                else if (deptData.key === prop.dept.id) { 
                    deptData.disabled = true
                    deptData.children = deptData.children ? handleTreeDataRecursion(deptData.children, false) : []; // 如果还有子集，就再次调用自己
                } 
                // 2）所选节点自身
                else if(selectedDeptIds.indexOf(deptData.key as string) > -1) {
                    deptData.disabled = true
                    deptData.children = deptData.children ? handleTreeDataRecursion(deptData.children, true) : []; // 如果还有子集，就再次调用自己
                }
                else {
                    deptData.disabled = false
                    deptData.children = deptData.children ? handleTreeDataRecursion(deptData.children, false) : []; // 如果还有子集，就再次调用自己
                }
            });
        }
        return data;
    }

    const columns: ProColumns<DeptData>[] = [
        {
            title: '名称',
            dataIndex: 'name'
        },
        {
            title: '类型',
            align: 'center',
            dataIndex: 'typeName'
        },
        {
            title: '排序',
            align: 'center',
            dataIndex: 'sort'
        },
        {
            title: '状态',
            align: 'center',
            dataIndex: 'enabled',
            render: (_: any, record: any) => [
                <Switch key="enabled" checked={record.enabled} checkedChildren="启用" unCheckedChildren="停用"
                    onClick={() => {
                        if (record.enabled) {
                            disableDept(record.id)
                        } else {
                            enableDept(record.id)
                        }
                        deptActionRef.current?.reload()
                    }} />
            ]
        },
        {
            title: '创建时间',
            align: 'center',
            dataIndex: 'createTime'
        },
        {
            title: '操作',
            key: 'option',
            align: 'center',
            valueType: 'option',
            render: (_: any, record: any) => [
                <a
                    key="update"
                    onClick={() => {
                        setDeptFormVisible(true);
                        setDeptCurrentRow(record);
                    }}
                >
                    编辑
                </a>,
                <Popconfirm key="deleteConfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
                    onConfirm={async () => {
                        await YuCrud.handleDelete(deleteDept, record.id);
                        if (deptActionRef.current) {
                            deptActionRef.current.reload();
                            prop.deptChange()
                        }
                    }}
                >
                    <a key="delete" href="#">
                        删除
                    </a>
                </Popconfirm>
            ],
        },
    ];

    return (
        <Fragment>
            <ProTable<DeptData>
                columns={columns}
                rowSelection={{
                    // 自定义选择项参考: https://ant.design/components/table-cn/#components-table-demo-row-selection-custom
                    // 注释该行则默认不显示下拉选项
                    selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
                    defaultSelectedRowKeys: [],
                }}
                tableAlertRender={({ selectedRowKeys, onCleanSelected }) => (
                    <Space size={24}>
                        <span>
                            已选 {selectedRowKeys.length} 项
                            <a style={{ marginLeft: 8 }} onClick={onCleanSelected}>
                                取消选择
                            </a>
                        </span>
                    </Space>
                )}
                tableAlertOptionRender={({ selectedRowKeys }) => {
                    setSelectedDeptIds(selectedRowKeys as string[])
                    return (
                        <Space size={16}>
                            {/* <a >合并</a> */}
                            <a onClick={() => { setMergeDeptModelVisble(true); }}>移动</a>
                        </Space>
                    );
                }}
                actionRef={deptActionRef}
                params={{ pid: prop.dept.id }}
                request={(params) => queryDept(params)}
                postData={(dataList) => {
                    return dataList;
                }}
                rowKey="id"
                search={false}
                dateFormatter="string"
                headerTitle="子部门管理"
                toolBarRender={() => [
                    <Button
                        type="primary"
                        key="primary"
                        onClick={() => {
                            deptFormRef?.current?.resetFields();
                            setDeptCurrentRow(undefined)
                            setDeptFormVisible(true);
                        }}
                    >
                        <PlusOutlined /> 新建
                    </Button>,
                ]}
            /> {deptFormVisible &&
                <DeptForm
                    formRef={deptFormRef}
                    visible={deptFormVisible}
                    initialValues={deptCurrentRow || { pid: prop.dept.id, pName: prop.dept.name, enabled: true }}
                    isAdd={!deptCurrentRow?.id}
                    pDept={prop.dept as DeptData}
                    onVisibleChange={(visible) => {
                        if (visible) {
                            deptFormRef?.current?.resetFields();
                        } else {
                            setDeptFormVisible(false);
                            setDeptCurrentRow(undefined);
                        }
                    }}
                    onFinish={async () => {
                        setDeptFormVisible(false);
                        deptFormRef?.current?.resetFields();
                        if (deptActionRef.current) {
                            deptActionRef.current.reload();
                            prop.deptChange()
                        }
                    }}
                />
            }
            {mergeDeptModelVisible &&
                <Modal title="部门移动"
                    visible={mergeDeptModelVisible}
                    onOk={async () => { 
                        const success = await YuCrud.handle({targetId: targetDeptId as string, sourceIds: selectedDeptIds}, moveDept);
                        if(success) {
                            deptActionRef.current?.reload();
                            if(deptActionRef.current?.clearSelected) {
                                deptActionRef.current?.clearSelected();
                                setSelectedDeptIds([])
                            }
                            
                            prop.deptChange()
                            setMergeDeptModelVisble(false) 
                        }
                        
                    }}
                    onCancel={() => { setMergeDeptModelVisble(false) }}
                    okButtonProps={{ disabled: !targetDeptId }}
                    >
                    <Title level={5}>目标部门</Title>
                    <Tree
                        showIcon
                        switcherIcon={<DownOutlined />}
                        treeData={handleTreeDataRecursion(prop.deptTree, false)}
                        defaultExpandAll
                        height={500}
                        onSelect={
                            (selectedKeys) => {
                                if (selectedKeys[0]) {
                                    setTargetDeptId(selectedKeys[0] as string)
                                    //setDept({ id: e.node.key as string, name: e.node.title as string, typeId: (e.node as (EventDataNode & { deptTypeId: string })).deptTypeId })
                                }
                            }
                        }
                    // onRightClick
                    />
                </Modal>
            }
        </Fragment>
    );
};

export default DeptPage;