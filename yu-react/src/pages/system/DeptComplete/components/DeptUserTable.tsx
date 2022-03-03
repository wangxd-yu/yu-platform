import React, { Fragment, useRef, useState } from 'react';
import type { FormInstance} from 'antd';
import { Popconfirm } from 'antd';
import { Button } from 'antd';
import { Avatar, Space } from 'antd';
import { Switch } from 'antd';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import type { UserData } from '../../User/data';
import ProTable from '@ant-design/pro-table';

import { yuUrlSystem } from '@/utils/yuUrl';
import * as YuCrud from '@/utils/yuCrud';
import * as YuApi from '@/utils/yuApi';
import { PlusOutlined, UserOutlined } from '@ant-design/icons';
import type { DataNode } from 'antd/lib/tree';
import UserForm from '../../User/components/UserForm';
import { deleteUser, disableUser, enableUser, getUser } from '../../User/service';

const DeptPage: React.FC<{ deptId: string, deptTree: DataNode[] }> = (prop: { deptId: string, deptTree: DataNode[] }) => {
  const [userFormVisible, setUserFormVisible] = useState<boolean>(false);
  const [userCurrentRow, setUserCurrentRow] = useState<UserData>();
  const [deptTree] = useState<DataNode[]>(prop.deptTree);
  const userFormRef = useRef<FormInstance>();
  const userActionRef = useRef<ActionType>();
  const columns: ProColumns<UserData>[] = [{
    dataIndex: 'avatar',
    title: '头像',
    align: 'center',
    valueType: 'avatar',
    width: 150,
    render: (dom, record) => (
      <Space>
        {record.portraitUrl ?
          <Avatar src={`/${BASE_URL_PREFIX}/${record.portraitUrl}`} /> :
          <Avatar icon={<UserOutlined />} />
        }
      </Space>
    ),
  },
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
      <Switch key="enabled" checked={record.enabled} checkedChildren="启用" unCheckedChildren="停用" onChange={() => {
        if (record.enabled) {
          disableUser(record.id)
        } else {
          enableUser(record.id)
        }
        userActionRef.current?.reload()
      }} />
    ],
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
      <Popconfirm key="deleteConfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
        onConfirm={async () => {
          await YuCrud.handleDelete(deleteUser, record.id)
          if (userActionRef.current) {
            userActionRef.current.reload();
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
      <ProTable<UserData>
        columns={columns}
        actionRef={userActionRef}
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
        params={{ deptId: prop.deptId }}
        request={(params) => YuApi.queryPage<UserData>(yuUrlSystem('/user'), params)}
        postData={(dataList) => {
          return dataList;
        }}
        rowKey="id"
        search={false}
        dateFormatter="string"
        headerTitle="用户列表"
      />
      {userFormVisible && <UserForm
        deptTree={deptTree}
        width="700px"
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
        initialValues={userCurrentRow || { enabled: true, deptId: prop.deptId }}
        onFinish={async () => {
          setUserFormVisible(false);
          userFormRef?.current?.resetFields();
          if (userActionRef.current) {
            userActionRef.current.reload();
          }
        }}
      />
      }
    </Fragment>
  );
};

export default DeptPage;