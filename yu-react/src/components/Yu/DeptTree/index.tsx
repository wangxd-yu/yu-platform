import React, { useEffect, useMemo, useState } from 'react'
import Search from 'antd/lib/input/Search';
import Tree, { DataNode, EventDataNode } from 'antd/lib/tree';
import { DeptData } from '@/pages/system/DeptComplete/data';
import { GlobalOutlined, ClusterOutlined } from '@ant-design/icons';
import { useModel } from 'umi';
import { yuUrlSystem } from '@/utils/yuUrl';
import * as YuApi from '@/utils/yuApi';

export type DeptTreeProps = {
    onInit?: (deptTree: DataNode[]) => void;  //初始化时调用
    onSelect: (deptId: string) => void;    //选中接点时调用   
}

const handleTreeDataRecursion = (data: DeptData[]): DataNode[] => {
    const item: DataNode[] = [];
    if (Array.isArray(data)) {
        data?.forEach((deptData: DeptData) => {
            const newData: DataNode & { value: string, deptTypeId: string, name: string } = {} as DataNode & { value: string, deptTypeId: string, name: string };
            if (deptData.id === '0') {
                newData.icon = <GlobalOutlined />
            } else {
                newData.icon = <ClusterOutlined />
            }
            newData.key = deptData.id as string;
            newData.value = deptData.id as string;
            newData.name = deptData.name as string;
            newData.title = deptData.name as string;
            newData.deptTypeId = deptData.typeId as string;
            newData.children = deptData.children ? handleTreeDataRecursion(deptData.children) : []; // 如果还有子集，就再次调用自己
            item.push(newData);
        });
    }
    return item;
}


const getParentKey = (key: String, tree: DataNode[]): React.Key => {
    let parentKey: React.Key;
    for (let i = 0; i < tree.length; i++) {
        const node = tree[i];
        if (node.children) {
            if (node.children.some((item) => item.key === key)) {
                parentKey = node.key;
            } else if (getParentKey(key, node.children)) {
                parentKey = getParentKey(key, node.children);
            }
        }
    }
    return parentKey!;
};


const DeptTree: React.FC<DeptTreeProps> = ((props: DeptTreeProps) => {
    const { initialState } = useModel('@@initialState');
    const { currentUser } = initialState || {};
    const [deptId, setDeptId] = useState<string>(currentUser?.deptId as string);
    const [dept, setDept] = useState<DeptData>({
        id: currentUser?.deptId as string,
        name: currentUser?.deptName as string,
        typeId: currentUser?.deptTypeId as string
    });
    const [deptTree, setDeptTree] = useState<DataNode[]>();
    const [deptList, setDeptList] = useState<DataNode[]>([]);

    const [expandedKeys, setExpandedKeys] = useState<React.Key[]>([currentUser?.deptId as string]);
    const [searchValue, setSearchValue] = useState('');
    const [autoExpandParent, setAutoExpandParent] = useState(true);

    useEffect(() => {
        initDeptTree()
    }, []);

    const initDeptTree = () => {
        YuApi.queryList<DeptData>(yuUrlSystem('/dept/tree')).then(res => {
            if (res?.data) {
                const deptTree = handleTreeDataRecursion(res?.data)
                setDeptTree(deptTree);
                generateList(res?.data)
                if (props.onInit) {
                    props.onInit(deptTree);
                }
            }
        });
    }

    const generateList = (data: DeptData[]) => {
        for (let i = 0; i < data.length; i++) {
            const node = data[i];
            setDeptList(current => [...current, { key: node.id as string, title: node.name }])
            if (node.children) {
                generateList(node.children);
            }
        }
    };

    const onExpand = (newExpandedKeys: React.Key[]) => {
        setExpandedKeys(newExpandedKeys);
        setAutoExpandParent(false);
    };

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { value } = e.target;
        if (value) {
            const newExpandedKeys = deptList?.map((item) => {
                if ((String)(item?.title)?.indexOf(value) > -1) {
                    return getParentKey(item.key as string, deptTree as DataNode[]);
                }
                return null;
            }).filter((item, i, self) => item && self.indexOf(item) === i);

            setExpandedKeys((newExpandedKeys).length > 0 ? newExpandedKeys as React.Key[] : [currentUser?.deptId as string]);
            setSearchValue(value);
            setAutoExpandParent(true);
        } else {
            setExpandedKeys([currentUser?.deptId as string]);
        }
    };

    const treeData = useMemo(() => {
        const loop = (data: DataNode[]): DataNode[] =>
            data.map((item) => {
                const strTitle = item.title as string;
                const index = strTitle.indexOf(searchValue);

                const beforeStr = strTitle.substring(0, index);
                const afterStr = strTitle.slice(index + searchValue.length);
                const title =
                    index > -1 ? (
                        <span>
                            {beforeStr}
                            <span style={{ color: '#f50' }}>{searchValue}</span>
                            {afterStr}
                        </span>
                    ) : (
                        <span>{strTitle}</span>
                    );
                const icon = item.key === '0' ? <GlobalOutlined /> : <ClusterOutlined />;
                if (item.children) {
                    return { title, icon, key: item.key as string, children: loop(item.children) };
                }



                return {
                    title: title,
                    key: item.key as string,
                    icon: <GlobalOutlined />
                };
            });
        if (deptTree) {
            return loop(deptTree);
        } else {
            return null
        }
    }, [searchValue]);

    return (
        <>
            <Search style={{ marginBottom: 8 }} placeholder="部门搜索" onChange={onChange} />
            {deptTree &&
                <Tree
                    showIcon
                    onExpand={onExpand}
                    blockNode={true}
                    expandedKeys={expandedKeys}
                    autoExpandParent={autoExpandParent}
                    style={{ fontSize: '16px' }}
                    defaultSelectedKeys={[currentUser?.deptId as string]}
                    /* switcherIcon={<DownOutlined />} */
                    treeData={treeData ? treeData : deptTree}
                    selectedKeys={[deptId]}
                    onSelect={
                        (selectedKeys, e) => {
                            if (selectedKeys[0]) {
                                setDeptId(selectedKeys[0] as string)
                                setDept({
                                    id: e.node.key as string,
                                    name: e.node.title as string,
                                    typeId: (e.node as (EventDataNode<string> & { deptTypeId: string })).deptTypeId
                                })

                                //回调
                                props.onSelect(selectedKeys[0] as string);
                            }
                        }
                    }
                />
            }
        </>
    )
})

export default DeptTree;