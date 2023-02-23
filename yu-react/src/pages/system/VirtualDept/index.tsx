import React, { useEffect, useState } from 'react';
import { Dropdown, Input, Menu, Tree } from 'antd';
import ProCard from '@ant-design/pro-card';
import './index.less'
import { EllipsisOutlined } from '@ant-design/icons';
import SplitPane from 'react-split-pane';
import { DataNode, EventDataNode } from 'antd/lib/tree';
import { yuUrlSystem } from '@/utils/yuUrl';
import { DeptData } from '../Dept/data';

import * as YuApi from '@/utils/yuApi';
import * as YuCrud from '@/utils/yuCrud';
import VirtualDeptForm from './components/VirtualDeptForm';
import ProForm from '@ant-design/pro-form';
import { FooterToolbar, PageContainer } from '@ant-design/pro-layout';
const { Search } = Input;

const handleTreeDataRecursion = (data: DeptData[]): DataNode[] => {
    const item: DataNode[] = [];
    if (Array.isArray(data)) {
        data?.forEach((deptData: DeptData) => {
            const newData: DataNode & { value: string } = {} as DataNode & { value: string };
            newData.key = deptData.id as string;
            newData.value = deptData.id as string;
            newData.title = deptData.name;
            newData.children = deptData.children ? handleTreeDataRecursion(deptData.children) : []; // 如果还有子集，就再次调用自己
            item.push(newData);
        });
    }
    return item;
}
const VirtualDeptPage: React.FC<{}> = () => {
    const [showVirtualDeptForm, setShowVirtualDeptForm] = useState<boolean>(false);
    // 部门节点 list 结构
    const [virtualDeptList, setVirtualDeptList] = useState<DeptData[]>([]);
    // 部门节点 tree 结构
    const [virtualDeptTree, setVirtualDeptTree] = useState<DataNode[]>();
    // 表单类型：A:新增，U：修改
    const [formType, setFormType] = useState<string>();
    // 当前 右键点击节点
    const [currentRightClickDataNode, setCurrentRightClickDataNode] = useState<API.VirtualDept>(null);

    // 当前选中的 节点
    const [currentSelectDataNode, setCurrentSelectDataNode] = useState<API.VirtualDept>(null);
    const [showFooterBtn, setShowFooterBtn] = useState<boolean>(false)
    const [deptCheckKeys, setDeptCheckKeys] = useState<string[]>([])

    const [deptTree, setDeptTree] = useState<DataNode[]>();

    const initDeptTree = () => {
        YuApi.queryList<DeptData>(yuUrlSystem('/dept/tree')).then(res => {
            setDeptTree(handleTreeDataRecursion(res?.data));
        });
    }

    const initVirtualDeptTree = () => {
        YuApi.queryList<DeptData>(yuUrlSystem('/virtualDept/tree')).then(res => {
            setVirtualDeptList(res.data)
            setVirtualDeptTree(handleTreeDataRecursion(res.data))
        });
    }

    useEffect(() => {
        initDeptTree()
        initVirtualDeptTree()
    }, []);
    const { DirectoryTree } = Tree;

    const handleTreeDataRecursion = (data: DeptData[]): DataNode[] => {
        const item: DataNode[] = [];
        if (Array.isArray(data)) {
            data?.forEach((deptData: DeptData) => {
                const newData: DataNode & { value: string; pid: string; } = {} as DataNode & { value: string; pid: string; };
                newData.pid = deptData.pid as string;
                newData.key = deptData.id as string;
                newData.value = deptData.id as string;
                newData.title = deptData.name;
                newData.children = deptData.children ? handleTreeDataRecursion(deptData.children) : []; // 如果还有子集，就再次调用自己
                item.push(newData);
            });
        }
        return item;
    }

    /**
     * 初始化右侧树
     */
    const initRightTree = (nodeData: EventDataNode<string>) => {
        setShowFooterBtn(false)
        setDeptCheckKeys([])
        YuApi.queryList<DeptData>(yuUrlSystem(`/virtualDept/${nodeData.key}/deptIds`)).then(res => setDeptCheckKeys(res as unknown as string[]))
    }

    function handleMenuClick(e: any) {
        switch (e.key) {
            case '1':
                setFormType('A')
                setShowVirtualDeptForm(true)
                break;
            case '2':
                setFormType('U')
                setShowVirtualDeptForm(true)
        }
    }

    const menuPopulate = (nodeData: DataNode & { pid: string }) => {
        return (
            <Menu onClick={event => {
                event.domEvent.stopPropagation();
                handleMenuClick(event)
            }}>
                {
                    nodeData.pid !== '0' &&
                    <Menu.Item key="1" >
                        {nodeData.key === '1' ? '新建虚拟组织' : '新建虚拟部门'}
                    </Menu.Item>
                }
                <Menu.Item key="2" >
                    修改信息
                </Menu.Item>
            </Menu>
        )
    };
    return (
        <PageContainer
            header={{
                breadcrumb: {},
            }}>
            <ProCard split="vertical" style={{ height: 'calc(100vh - 170px)' }}>
                <SplitPane split="vertical" defaultSize={300} minSize={300} maxSize={400}>
                    <div style={{ width: '100%' }}>
                        <div style={{ padding: '5px' }}>
                            <Search placeholder="查询" onChange={() => { }} />
                        </div>

                        <div style={{ height: 'calc(100% - 45px)', overflow: 'auto' }}>
                            <DirectoryTree
                                titleRender={(nodeData) => {
                                    return (
                                        <>
                                            <span>{nodeData.title}</span>
                                            <span className='right'>
                                                <Dropdown overlay={menuPopulate(nodeData)} trigger={['click']} placement="bottomRight">
                                                    <EllipsisOutlined onClick={event => {
                                                        setCurrentRightClickDataNode(nodeData)
                                                        event.stopPropagation()
                                                    }} />
                                                </Dropdown>
                                            </span>
                                        </>
                                    )
                                }}
                                onClick={(event, nodeData) => {
                                    setCurrentSelectDataNode(nodeData)
                                    initRightTree(nodeData)
                                }}
                                defaultExpandedKeys={['0-0-0']}
                                //onSelect={this.onSelect}
                                treeData={virtualDeptTree}
                            />
                        </div>
                    </div>
                    <ProCard title="映射关系" headerBordered>
                        <div style={{ height: 'calc(100% - 40px)', overflow: 'auto' }}>
                            <ProForm
                                submitter={{
                                    render: (_, dom) => {
                                        if (showFooterBtn) {
                                            return <FooterToolbar>{dom}</FooterToolbar>
                                        }
                                        return null
                                    },
                                }}
                                onFinish={async (values) => {
                                    //YuApi.add({deptIds: deptCheckKeys}, yuUrlSystem(`/virtualDept/${currentSelectDataNode.key}/deptIds`))
                                    await YuCrud.handleAdd({ deptIds: deptCheckKeys }, ({ deptIds: deptCheckKeys }) => {
                                        return YuApi.add({ deptIds: deptCheckKeys }, yuUrlSystem(`/virtualDept/${currentSelectDataNode.key}/deptIds`))
                                    });

                                    setShowFooterBtn(false)
                                }}
                            >
                                {
                                    deptTree &&
                                    <Tree
                                        showIcon
                                        checkable
                                        defaultExpandAll
                                        /* defaultExpandedKeys={[currentUser?.deptId as string]}
                                        defaultSelectedKeys={[currentUser?.deptId as string]}
                                        switcherIcon={<DownOutlined />} */
                                        treeData={deptTree}
                                        onCheck={
                                            (selectedKeys, e) => {
                                                setShowFooterBtn(true)
                                                setDeptCheckKeys(selectedKeys as string[])
                                            }
                                        }
                                        checkedKeys={deptCheckKeys}
                                    /*onSelect={
                                        (selectedKeys, e) => {
                                        if (selectedKeys[0]) {
                                            setDeptId(selectedKeys[0] as string)
                                            setDept({
                                            id: e.node.key as string,
                                            name: e.node.title as string,
                                            typeId: (e.node as (EventDataNode & { deptTypeId: string })).deptTypeId
                                            })
                                        }
                                        }
                                    } */
                                    // onRightClick
                                    />
                                }
                            </ProForm>
                        </div>
                    </ProCard>
                </SplitPane>
            </ProCard>

            {(showVirtualDeptForm && formType === 'A') &&
                <VirtualDeptForm
                    visible={showVirtualDeptForm}
                    onVisibleChange={(visible: boolean) => {
                        if (!visible) {
                            setShowVirtualDeptForm(false)
                        }
                    }}
                    onFinish={
                        async () => {
                            setShowVirtualDeptForm(false)
                            initVirtualDeptTree()
                        }
                    }
                    initialValues={{ pid: currentRightClickDataNode.value }}
                    pDept={currentRightClickDataNode} />
            }

            {(showVirtualDeptForm && formType === 'U') &&
                <VirtualDeptForm
                    visible={showVirtualDeptForm}
                    params={{ id: currentRightClickDataNode.value }}
                    request={async (params) => {
                        const value = await YuApi.getById<DeptData>(params.id, yuUrlSystem(`/virtualDept`))
                        return Promise.resolve(value)
                    }}
                    onVisibleChange={(visible: boolean) => {
                        if (!visible) {
                            setShowVirtualDeptForm(false)
                        }
                    }}
                    onFinish={
                        async () => {
                            setShowVirtualDeptForm(false)
                            initVirtualDeptTree()
                        }
                    }
                    initialValues={{ pid: currentRightClickDataNode.pid }}
                    pDept={virtualDeptList.find(item => item.id === currentRightClickDataNode.pid)} />
            }
        </PageContainer>
    );
}

export default VirtualDeptPage;