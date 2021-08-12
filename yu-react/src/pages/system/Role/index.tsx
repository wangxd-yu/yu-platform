import { FolderOutlined, PlusOutlined, ProfileOutlined, SecurityScanOutlined } from '@ant-design/icons';
import { Button, Tree } from 'antd';
import type { FormInstance } from 'antd';
import React, { useState, useRef, useEffect } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import ProForm, { DrawerForm, } from '@ant-design/pro-form';
import { queryRole, addRole, updateRole, deleteRole, saveRoleMenus, getRoleMenus } from './service';
import * as YuCrud from '@/utils/yuCrud';
import type { RoleData, TableListPagination } from './data';
import RoleForm from './components/RoleForm'
import type { MenuData } from '../Menu/data';
import { queryMenu } from '../Menu/service';
import type { DataNode } from 'rc-tree/lib/interface';
import { MenuTypeEnum } from '../Menu/index';

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
  const actionRef = useRef<ActionType>();
  const [roleCurrentRow, setRoleCurrentRow] = useState<RoleData>();
  const [menuTree, setMenuTree] = useState<DataNode[]>();
  const [checkedKeys, setCheckedKeys] = useState<CheckedKye>();
  const onCheck = (checkedKeysValue: CheckedKye | React.Key[]) => {
    setCheckedKeys(checkedKeysValue as CheckedKye);
  };

  useEffect(() => {
    queryMenu().then(res => {
      setMenuTree(handleTreeDataRecursion(res.data));
    });
  }, []);

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
        <a key="subscribeAlert"
          onClick={async () => {
            await YuCrud.handleDelete(record.id, deleteRole)
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }}>
          删除
        </a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<RoleData, TableListPagination>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
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
        ]}
        request={queryRole}
        columns={columns}
      />
      <RoleForm
        width="500px"
        title={!roleCurrentRow?.id ? '更新角色' : '新建角色'}
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
    </PageContainer >
  );
};

export default RoleTable;