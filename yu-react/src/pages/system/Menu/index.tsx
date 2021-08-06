import React, { useState, useRef } from 'react';
import type { FormInstance } from 'antd';
import { Button, Tag, Space, Popconfirm } from 'antd';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import type { MenuData } from './data';
import { PlusOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import * as YuCrud from '@/utils/yuCrud';
import { queryMenu, addMenu, updateMenu, deleteMenu } from './service'
import MenuForm from './components/MenuForm'

export enum MenuTypeEnum {
  FOLDER = 'FOLDER',
  MENU = 'MENU',
  PERMISSION = 'PERMISSION'
}

export const menuTypeMap = new Map([[
  MenuTypeEnum.FOLDER, {
    label: "目录",
    value: MenuTypeEnum.FOLDER,
    color: "success"
  }
], [
  MenuTypeEnum.MENU, {
    label: "菜单",
    value: MenuTypeEnum.MENU,
    color: "processing"
  }
], [
  MenuTypeEnum.PERMISSION, {
    label: "权限",
    value: MenuTypeEnum.PERMISSION,
    color: "warning"
  }
]])

export const menuTypeArr = Array.from(menuTypeMap.values());

const MenuPage: React.FC = () => {
  const [menuFormVisible, setMenuFormVisible] = useState<boolean>(false);
  const [menuCurrentRow, setMenuCurrentRow] = useState<MenuData>();
  const [menuDataList, setMenuDataList] = useState<MenuData[]>();
  const menuFormRef = useRef<FormInstance>();
  const menuActionRef = useRef<ActionType>();

  const columns: ProColumns<MenuData>[] = [
    {
      title: '菜单名称',
      width: 180,
      dataIndex: 'name'
    },
    {
      title: '类型',
      width: 60,
      align: 'center',
      dataIndex: 'type',
      render: (_, record) => {
        const menuType = menuTypeArr.find(item => item.value === record.type);
        return (<Space>
          <Tag color={menuType?.color} key={menuType?.label}>
            {menuType?.label}
          </Tag>
        </Space>)
      },
    },
    {
      title: '图标',
      width: 30,
      align: 'center',
      dataIndex: 'icon'
    },
    {
      title: '排序',
      width: 30,
      dataIndex: 'sort'
    },
    {
      title: '路由路径',
      width: 160,
      dataIndex: 'path'
    },
    {
      title: '组件路径',
      width: 160,
      dataIndex: 'component'
    },
    {
      title: '权限编码',
      width: 120,
      dataIndex: 'permission'
    },
    {
      title: '创建时间',
      width: 140,
      align: 'center',
      dataIndex: 'createTime'
    },
    {
      title: '操作',
      width: 180,
      key: 'option',
      valueType: 'option',
      render: (_: any, record: any) => [
        <a
          key="update"
          onClick={() => {
            setMenuFormVisible(true);
            setMenuCurrentRow(record);
          }}
        >
          编辑
        </a>,
        <Popconfirm key="popconfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(record.id, deleteMenu);
            if (menuActionRef.current) {
              menuActionRef.current.reload();
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
      <ProTable<MenuData>
        columns={columns}
        actionRef={menuActionRef}
        request={queryMenu}
        postData={(dataList) => {
          setMenuDataList(dataList)
          return dataList;
        }}
        rowKey="id"
        pagination={{
          hideOnSinglePage: true,
        }}
        expandable={{
          expandRowByClick: false
        }}
        search={false}
        dateFormatter="string"
        headerTitle="菜单管理"
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              menuFormRef?.current?.resetFields();
              setMenuCurrentRow(undefined)
              setMenuFormVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
      />
      {menuDataList &&
        <MenuForm
          formRef={menuFormRef}
          visible={menuFormVisible}
          initialValues={menuCurrentRow || {}}
          isAdd={!menuCurrentRow?.id}
          menuDataList={menuDataList}
          onVisibleChange={(visible) => {
            if (visible) {
              menuFormRef?.current?.resetFields();
            } else {
              setMenuFormVisible(false);
              setMenuCurrentRow(undefined);
            }
          }}
          onFinish={async (value) => {
            const data = { ...menuCurrentRow, ...value };
            let success;
            if (!data.id) {
              success = await YuCrud.handleAdd(data as MenuData, addMenu);
            } else {
              success = await YuCrud.handleUpdate(data as MenuData, updateMenu);
            }
            if (success) {
              setMenuFormVisible(false);
              menuFormRef?.current?.resetFields();
              if (menuActionRef.current) {
                menuActionRef.current.reload();
              }
            }
          }}
        />
      }
    </PageContainer>
  );
};

export default MenuPage;