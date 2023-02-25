import React, { useState, useEffect, useMemo } from 'react';
import type { DeptData } from './data';
import { PageContainer } from '@ant-design/pro-layout';
import { useModel } from 'umi';

import { Tree } from 'antd';
import {
  ClusterOutlined,
  GlobalOutlined,
} from '@ant-design/icons';
import ProCard from '@ant-design/pro-card';
import type { DataNode, EventDataNode } from 'antd/lib/tree';
import { yuUrlSystem } from '@/utils/yuUrl';
import * as YuApi from '@/utils/yuApi';
import DeptFormInner from './components/DeptFormInner';
import SubDeptTable from './components/SubDeptTable';
import DeptUserTable from './components/DeptUserTable';
import DeptRolePage from './components/DeptRoleTable';
import Search from 'antd/lib/input/Search';

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

const DeptPage: React.FC = () => {
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
  const [tab, setTab] = useState('tab1');


  const [expandedKeys, setExpandedKeys] = useState<React.Key[]>([currentUser?.deptId as string]);
  const [searchValue, setSearchValue] = useState('');
  const [autoExpandParent, setAutoExpandParent] = useState(true);

  useEffect(() => {
    initDeptTree()
  }, []);

  const initDeptTree = () => {
    YuApi.queryList<DeptData>(yuUrlSystem('/dept/tree')).then(res => {
      if (res?.data) {
        setDeptTree(handleTreeDataRecursion(res?.data));
        generateList(res?.data)
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
    if(value) {
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
    <PageContainer
      header={{
        breadcrumb: {},
      }}>
      <ProCard split="vertical" style={{ height: 'calc(100vh - 170px)' }}>
        <ProCard colSpan={{ xs: 20, sm: 16, md: 12, lg: 8, xl: 6 }}>
          {deptTree &&
            <>
              <Search style={{ marginBottom: 8 }} placeholder="部门搜索" onChange={onChange} />
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
                    }
                  }
                }
              />
            </>
          }
        </ProCard>
        <ProCard
          tabs={{
            tabPosition: "top",
            activeKey: tab,
            onChange: (key) => {
              setTab(key);
            },
          }}
        >
          <ProCard.TabPane key="tab1" tab="基本信息">
            {tab === 'tab1' &&
              <DeptFormInner
                id={deptId as string}
                dept={dept as DeptData}
                deptUpdate={initDeptTree}
              />
            }
          </ProCard.TabPane>
          <ProCard.TabPane key="tab2" tab="子部门">
            {tab === 'tab2' &&
              <SubDeptTable
                dept={dept as DeptData}
                deptTree={deptTree as DataNode[]}
                deptChange={initDeptTree}
              />
            }
          </ProCard.TabPane>
          <ProCard.TabPane key="tab3" tab="用户列表">
            {tab === 'tab3' &&
              <DeptUserTable
                deptId={deptId as string}
                deptTree={deptTree as DataNode[]}
              />
            }
          </ProCard.TabPane>
          <ProCard.TabPane key="tab4" tab="角色引用">
            {tab === 'tab4' &&
              <DeptRolePage deptId={deptId as string}
              />
            }
          </ProCard.TabPane>
        </ProCard>
      </ProCard>
    </PageContainer>
  );
};

export default DeptPage;