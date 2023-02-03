import React, { useState, useEffect, useRef } from 'react';
import { PlusOutlined, UserOutlined } from '@ant-design/icons';
import { Avatar, Button, Popconfirm, Popover, Space, Switch, TreeSelect } from 'antd';
import type { FormInstance } from 'antd';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { queryUser, getUser, deleteUser, disableUser, enableUser } from './service';
import * as YuCrud from '@/utils/yuCrud';
import type { UserData } from './data';
import UserForm from './components/UserForm'
import type { DataNode } from 'rc-tree/lib/interface';
import * as YuApi from '@/utils/yuApi';
import type { DeptData } from '../Dept/data';
import { yuUrlSystem } from '@/utils/yuUrl';

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

const UserTable: React.FC<UserData> = () => {
  /** 新建窗口的弹窗 */
  const [userFormVisible, setUserFormVisible] = useState<boolean>(false);
  const [userCurrentRow, setUserCurrentRow] = useState<UserData>();
  const [deptTree, setDeptTree] = useState<DataNode[]>();
  const userFormRef = useRef<FormInstance>();
  const userActionRef = useRef<ActionType>();

  useEffect(() => {
    YuApi.queryList<DeptData>(yuUrlSystem('/dept/tree')).then(res => {
      setDeptTree(handleTreeDataRecursion(res.data));
    });
  }, []);

  const columns: ProColumns<UserData>[] = [
    {
      title: '所属部门',
      dataIndex: 'deptId',
      hideInTable: true,
      renderFormItem: () => {
        return (
          <TreeSelect
            style={{ width: '100%' }}
            listHeight={300}
            dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
            treeData={deptTree}
            placeholder="请选择"
            treeDefaultExpandAll
          />
        );
      },
    },
    {
      dataIndex: 'avatar',
      title: '头像',
      align: 'center',
      valueType: 'avatar',
      width: 150,
      search: false,
      render: (dom, record) => (
        <Popover content={
          <Space>
            {record.portraitUrl ?
              <Avatar size={{ xs: 24, sm: 32, md: 40, lg: 64, xl: 80, xxl: 100 }} src={`/${BASE_URL_PREFIX}/${record.portraitUrl}`} /> :
              <Avatar size={{ xs: 24, sm: 32, md: 40, lg: 64, xl: 80, xxl: 100 }} icon={<UserOutlined />} />
            }
          </Space>
        } trigger="hover">
          <Space>
            {record.portraitUrl ?
              <Avatar src={`/${BASE_URL_PREFIX}/${record.portraitUrl}`} /> :
              <Avatar icon={<UserOutlined />} />
            }
          </Space>
        </Popover>
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
      dataIndex: 'deptFullName',
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
    <PageContainer>
      <ProTable<UserData, API.TableListPagination>
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
        initialValues={userCurrentRow || { enabled: true }}
        onFinish={async () => {
          setUserFormVisible(false);
          userFormRef?.current?.resetFields();
          if (userActionRef.current) {
            userActionRef.current.reload();
          }
        }}
      />
      }
    </PageContainer >
  );
};
export default UserTable;