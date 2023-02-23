import { FolderOutlined, MenuFoldOutlined, MenuUnfoldOutlined, PlusOutlined, ProfileOutlined, SecurityScanOutlined, UserOutlined } from '@ant-design/icons';
import { Avatar, Button, Popconfirm, Space, Tree } from 'antd';
import type { FormInstance } from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import ProForm, { DrawerForm, } from '@ant-design/pro-form';
import { queryRole, addRole, updateRole, deleteRole, saveRoleMenus, getRoleMenus, queryRoleUserPage } from './service';
import * as YuCrud from '@/utils/yuCrud';
import type { RoleData } from './data';
import RoleForm from './components/RoleForm'
import type { MenuData } from '../Menu/data';
import { queryMenu } from '../Menu/service';
import type { DataNode } from 'rc-tree/lib/interface';
import { MenuTypeEnum } from '../Menu/index';
import ProCard from '@ant-design/pro-card';

import styles from './role.less'
import type { UserData } from '../User/data';

const handleTreeDataRecursion = (data: MenuData[]): DataNode[] => {
  const item: DataNode[] = [];
  if (Array.isArray(data)) {
    data?.forEach((list: MenuData) => {
      const newData: DataNode = {} as DataNode;
      newData.key = list.id;
      newData.title = list.name;
      if (MenuTypeEnum[list.type] === MenuTypeEnum.FOLDER) {
        newData.icon = <FolderOutlined />;
      } else if (MenuTypeEnum[list.type] === MenuTypeEnum.MENU) {
        newData.icon = <ProfileOutlined />
      } else {
        newData.icon = <SecurityScanOutlined />
      }

      newData.children = list.children ? handleTreeDataRecursion(list.children) : []; // 如果还有子集，就再次调用自己
      item.push(newData);
    });
  }
  return item;
}

interface CheckedKye {
  checked: React.Key[];
  halfChecked: React.Key[];
}

const RoleTable: React.FC = () => {
  /** 新建窗口的弹窗 */
  const [createRoleVisible, handleRoleVisible] = useState<boolean>(false);
  const [showRoleMenuDetail, setShowRoleMenuDetail] = useState<boolean>(false);
  const roleFormRef = useRef<FormInstance>();
  const rightRoleFormRef = useRef<FormInstance>();
  const rightRoleMenuFormRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const [roleCurrentRow, setRoleCurrentRow] = useState<RoleData>();
  const [clickedRole, setClickedRole] = useState<RoleData>();
  const [menuTree, setMenuTree] = useState<DataNode[]>();
  const [checkedKeys, setCheckedKeys] = useState<CheckedKye>();

  const [showRight, setShowRight] = useState(true)
  const [tab, setTab] = useState('tab1');
  const onCheck = (checkedKeysValue: CheckedKye | React.Key[]) => {
    setCheckedKeys(checkedKeysValue as CheckedKye);
  };

  useEffect(() => {
    queryMenu().then(res => {
      setMenuTree(handleTreeDataRecursion(res.data));
    });
  }, []);

  const userColumns: ProColumns<UserData>[] = [
    {
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
      dataIndex: 'deptFullName',
      search: false,
    },
    {
      title: '创建时间',
      align: 'center',
      sorter: true,
      search: false,
      dataIndex: 'createTime',
      valueType: 'dateTime'
    }
  ];

  const columns: ProColumns<RoleData>[] = [
    {
      title: '角色名称',
      dataIndex: 'name',
    },
    {
      title: '角色编号',
      tip: '角色编号是唯一的 key',
      dataIndex: 'code',
      hideInForm: true
    },
    {
      title: '描述',
      dataIndex: 'remark',
      ellipsis: true,
      search: false,
      valueType: 'textarea',
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
          onClick={() => {
            setRoleCurrentRow(record);
            handleRoleVisible(true);
          }}
        >
          编辑
        </a>,
        <a
          key="config"
          onClick={async () => {
            setCheckedKeys({
              checked: await getRoleMenus(record.id),
              halfChecked: []
            });
            setShowRoleMenuDetail(true);
            setRoleCurrentRow(record);
          }}
        >
          权限分配
        </a>,
        <Popconfirm key="deleteConfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(deleteRole, record.id)
            if (actionRef.current) {
              actionRef.current.reload();
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
    <PageContainer header={{
      breadcrumb: {},
    }}>
      <ProCard split="vertical">
        <ProCard>
          <ProTable<RoleData>
            headerTitle="角色列表"
            actionRef={actionRef}
            options={false}
            rowKey="id"
            rowClassName={(record) => {
              return record.id === clickedRole?.id ? styles['role-row-select-active'] : '';
            }}
            onRow={(record) => {
              return {
                onClick: () => {
                  setClickedRole(record)
                  setRoleCurrentRow(record)
                  rightRoleFormRef?.current?.setFieldsValue(record)
                },
              };
            }}
            search={{
              filterType: 'light',
            }}
            toolBarRender={() => [
              <Button
                type="primary"
                key="primary"
                onClick={() => {
                  roleFormRef?.current?.resetFields();
                  setRoleCurrentRow(undefined)
                  handleRoleVisible(true);
                }}
              >
                <PlusOutlined /> 新建
              </Button>,
              <Button
                onClick={() => setShowRight(!showRight)}>
                {showRight ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
              </Button>,
            ]}
            request={queryRole}
            columns={!showRight ? columns : columns.filter(item => !['remark', 'createTime'].includes(item.dataIndex as string))}
          />
          <RoleForm
            width="500px"
            title={roleCurrentRow?.id ? '更新角色' : '新建角色'}
            visible={createRoleVisible}
            onVisibleChange={(visible) => {
              if (visible) {
                roleFormRef?.current?.resetFields();
              } else {
                handleRoleVisible(false);
                setRoleCurrentRow(undefined);
              }
            }}
            formRef={roleFormRef}
            initialValues={roleCurrentRow || {}}
            onFinish={async (value) => {
              const data = { ...roleCurrentRow, ...value };
              let success;
              if (!data.id) {
                success = await YuCrud.handleAdd(data as RoleData, addRole);
              } else {
                success = await YuCrud.handleUpdate(data as RoleData, updateRole);
              }
              if (success) {
                handleRoleVisible(false);
                roleFormRef?.current?.resetFields();
                if (actionRef.current) {
                  actionRef.current.reload();
                }
              }
            }}
          />
          <DrawerForm
            title="权限分配"
            width="600"
            visible={showRoleMenuDetail}
            onVisibleChange={(visible) => {
              setShowRoleMenuDetail(visible)
              if (!visible) {
                setCheckedKeys({ checked: [], halfChecked: [] });
              }
            }}
            onFinish={async () => {
              const data = {
                roleId: roleCurrentRow?.id as string,
                menuIds: checkedKeys?.checked as string[]
              };
              const success = await YuCrud.handle<{
                roleId: string;
                menuIds: string[];
              }>(data, saveRoleMenus, '权限分配');
              if (success) {
                setShowRoleMenuDetail(false);
              }
            }}
          >
            <ProForm.Item name="menuIds">
              <Tree
                checkable
                showIcon={true}
                checkStrictly={true}
                defaultExpandAll={true}
                onCheck={onCheck}
                checkedKeys={checkedKeys}
                treeData={menuTree}
              />
            </ProForm.Item>
          </DrawerForm>
        </ProCard>
        {showRight &&
          <ProCard colSpan="50%"
            title={'当前角色：' + (clickedRole ? clickedRole.name : '')}
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
                <RoleForm
                  style={{ textAlign: 'center' }}
                  formType='ProForm'
                  formRef={rightRoleFormRef}
                  initialValues={roleCurrentRow || {}}
                  onFinish={async (value) => {
                    const data = { ...roleCurrentRow, ...value };
                    let success;
                    if (!data.id) {
                      success = await YuCrud.handleAdd(data as RoleData, addRole);
                    } else {
                      success = await YuCrud.handleUpdate(data as RoleData, updateRole);
                    }
                    if (success) {
                      if (actionRef.current) {
                        actionRef.current.reload();
                      }
                    }
                  }}
                />
              }
            </ProCard.TabPane>
            <ProCard.TabPane key="tab2" tab="权限">
              {tab === 'tab2' &&
                <ProForm

                  style={{ textAlign: 'center' }}
                  params={{ id: roleCurrentRow?.id }}
                  formRef={rightRoleMenuFormRef}
                  request={async (params) => {
                    const value = await getRoleMenus(params.id);
                    setCheckedKeys({
                      checked: Object.values(value),
                      halfChecked: []
                    });
                    
                    return Promise.resolve(value);
                  }}
                >
                  <ProForm.Item name="menuIds">
                    <Tree
                      checkable
                      showIcon={true}
                      checkStrictly={true}
                      defaultExpandAll={true}
                      onCheck={onCheck}
                      checkedKeys={checkedKeys}
                      treeData={menuTree}
                    />
                  </ProForm.Item>
                </ProForm>
              }
            </ProCard.TabPane>
            <ProCard.TabPane key="tab3" tab="人员">
              {tab === 'tab3' &&
                <ProTable<UserData, { roleId?: string }>
                  columns={userColumns}
                  rowKey="id"
                  params={{ roleId: clickedRole?.id }}
                  request={queryRoleUserPage}
                  search={false}
                  dateFormatter="string"
                  options={false}
                />
              }
            </ProCard.TabPane>
          </ProCard>
        }
      </ProCard>
    </PageContainer>
  );
};

export default RoleTable;