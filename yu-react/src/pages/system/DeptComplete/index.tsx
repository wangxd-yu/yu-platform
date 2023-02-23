import React, { useState, useEffect } from 'react';
import type { DeptData } from './data';
import { PageContainer } from '@ant-design/pro-layout';
import { useModel } from 'umi';

import { Tree } from 'antd';
import {
  ClusterOutlined,
  DownOutlined, GlobalOutlined,
} from '@ant-design/icons';
import ProCard from '@ant-design/pro-card';
import type { DataNode, EventDataNode } from 'antd/lib/tree';
import { yuUrlSystem } from '@/utils/yuUrl';
import * as YuApi from '@/utils/yuApi';
import DeptForm from './components/DeptForm';
import SubDeptTable from './components/SubDeptTable';
import DeptUserTable from './components/DeptUserTable';
import DeptRolePage from './components/DeptRoleTable';

const handleTreeDataRecursion = (data: DeptData[]): DataNode[] => {
  const item: DataNode[] = [];
  if (Array.isArray(data)) {
    data?.forEach((deptData: DeptData) => {
      const newData: DataNode & { value: string, deptTypeId: string } = {} as DataNode & { value: string, deptTypeId: string };
      if (deptData.id === '0') {
        newData.icon = <GlobalOutlined />
      } else {
        newData.icon = <ClusterOutlined />
      }
      newData.key = deptData.id as string;
      newData.value = deptData.id as string;
      newData.title = deptData.name;
      newData.deptTypeId = deptData.typeId as string;
      newData.children = deptData.children ? handleTreeDataRecursion(deptData.children) : []; // 如果还有子集，就再次调用自己
      item.push(newData);
    });
  }
  return item;
}

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
  const [tab, setTab] = useState('tab1');

  const initDeptTree = () => {
    YuApi.queryList<DeptData>(yuUrlSystem('/dept/tree')).then(res => {
      setDeptTree(handleTreeDataRecursion(res?.data));
    });
  }

  useEffect(() => {
    initDeptTree()
  }, []);

  return (
    <PageContainer
      header={{
        breadcrumb: {},
      }}>
      <ProCard split="vertical" style={{ height: 'calc(100vh - 170px)' }}>
        <ProCard colSpan={{ xs: 20, sm: 16, md: 12, lg: 8, xl: 6 }}>
          {deptTree &&
            <Tree
              showIcon
              style={{ fontSize: '16px' }}
              defaultExpandedKeys={[currentUser?.deptId as string]}
              defaultSelectedKeys={[currentUser?.deptId as string]}
              switcherIcon={<DownOutlined />}
              treeData={deptTree}
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
            // onRightClick
            />
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
              <DeptForm
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