import React, { useState, useEffect, useRef } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { Button, Switch } from 'antd';
import type { FormInstance } from 'antd';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { queryUser,getUser, addUser, updateUser, deleteUser,disableUser,enableUser } from './service';
import * as YuCrud from '@/utils/yuCrud';
import type { UserData, TableListPagination } from './data';
import UserForm from './components/UserForm'
import type { DataNode } from 'rc-tree/lib/interface';
import * as YuApi from '@/utils/yuApi';
import type { DeptData } from '../Dept/data';
import { yuUrlSystem } from '@/utils/yuUrl';

const handleTreeDataRecursion = (data: DeptData[]): DataNode[] => {
  const item: DataNode[] = [];
  if (Array.isArray(data)) {
    data?.forEach((deptData: DeptData) => {
      const newData: DataNode = {} as DataNode;
      newData.key = deptData.no as string;
      newData.title = deptData.name;
      newData.children = deptData.children ? handleTreeDataRecursion(deptData.children) : []; // 如果还有子集，就再次调用自己
      item.push(newData);
    });
  }
  return item;
}

const UserTable: React.FC<UserData> = () => {
  /** 新建窗口的弹窗 */
  const [userFormVisible, setUserFormVisible] = useState<boolean>(false);
  const [userCurrentRow, setUserCurrentRow] = useState<UserData>();
  const [deptTree, setDeptTree] = useState<DataNode[]>();
  const userFormRef = useRef<FormInstance>();
  const userActionRef = useRef<ActionType>();

  useEffect(() => {
    YuApi.queryList<DeptData>(yuUrlSystem('/dept')).then(res => {
      setDeptTree(handleTreeDataRecursion(res.data));
    });
  }, []);

  const columns: ProColumns<UserData>[] = [
    {
      title: '用户账号',
      tip: '用户账号是唯一的 key',
      dataIndex: 'username'
    },
    {
      title: '用户昵称',
      dataIndex: 'name',
    },
    {
      title: '所属部门',
      dataIndex: 'deptName',
      search: false,
    },
    {
      title: '状态',
      align: 'center',
      dataIndex: 'enabled',
      valueType: 'select',
      valueEnum: {
        true: {
          text: '启用',
        },
        false: {
          text: '停用',
        }
      },
      render: (_: any, record: any) => [
        <Switch key="enabled" checked={record.enabled} checkedChildren="启用" unCheckedChildren="停用"  onChange={() => {
          if(record.enabled) {
            disableUser(record.id)
          } else {
            enableUser(record.id)
          }
          userActionRef.current?.reload()
        }}/>
      ],
      
    },
    {
      title: '创建时间',
      align: 'center',
      sorter: true,
      search: false,
      dataIndex: 'createTime',
      valueType: 'dateTime'
    },
    {
      title: '操作',
      align: 'center',
      dataIndex: 'option',
      valueType: 'option',
      render: (_: any, record: any) => [
        <a
          key="update"
          onClick={async () => {
            await getUser(record.id, setUserCurrentRow)
            setUserFormVisible(true);
          }}
        >
          编辑
        </a>,
        <a key="subscribeAlert"
          onClick={async () => {
            await YuCrud.handleDelete(deleteUser, record.id)
            if (userActionRef.current) {
              userActionRef.current.reload();
            }
          }}>
          删除
        </a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<UserData, TableListPagination>
        headerTitle="查询表格"
        actionRef={userActionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              userFormRef?.current?.resetFields();
              setUserCurrentRow(undefined)
              setUserFormVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={queryUser}
        columns={columns}
      />
      <UserForm
        deptTree={deptTree}
        width="500px"
        title={userCurrentRow?.id ? '更新用户' : '新建用户'}
        visible={userFormVisible}
        onVisibleChange={(visible) => {
          if (visible) {
            userFormRef?.current?.resetFields();
          } else {
            setUserFormVisible(false);
            setUserCurrentRow(undefined);
          }
        }}
        formRef={userFormRef}
        initialValues={userCurrentRow || {enabled: true}}
        onFinish={async (value) => {
          const data = { ...userCurrentRow, ...value };
          let success;
          if (!data.id) {
            success = await YuCrud.handleAdd(data as UserData, addUser);
          } else {
            success = await YuCrud.handleUpdate(data as UserData, updateUser);
          }
          if (success) {
            setUserFormVisible(false);
            userFormRef?.current?.resetFields();
            if (userActionRef.current) {
              userActionRef.current.reload();
            }
          }
        }}
      />
    </PageContainer >
  );
};
export default UserTable;