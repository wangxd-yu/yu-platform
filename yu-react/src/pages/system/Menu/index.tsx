import React, { useState, useRef } from 'react';
import type { FormInstance } from 'antd';
import { Modal } from 'antd';
import { Drawer } from 'antd';
import { Button, Tag, Space, Popconfirm } from 'antd';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import type { MenuData } from './data';
import { PlusOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import * as YuCrud from '@/utils/yuCrud';
import { queryMenu, addMenu, updateMenu, deleteMenu, queryMenuEndponits, queryEndponitList, saveMenuEndpoints, deleteMenuEndpoint } from './service'
import MenuForm from './components/MenuForm'
import type { EndpointData } from '../Endpoint/data';
import { methodMap } from '../Endpoint';

import _ from 'lodash'

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
  const [showMenuEndpointsDetail, setShowMenuEndpointsDetail] = useState<boolean>(false);
  const [currentMenuEndpointIds, setCurrentMenuEndpointIds] = useState<string[]>([]);
  const [showEndpointsModal, setShowEndpointsModal] = useState<boolean>(false);
  const menuFormRef = useRef<FormInstance>();
  const menuActionRef = useRef<ActionType>();
  const menuEndpointsActionRef = useRef<ActionType>();

  const [selectedEndpoints, setSelectedEndpoints] = useState<React.Key[]>();

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
      render: (__, record) => {
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
      align: 'center',
      key: 'option',
      valueType: 'option',
      render: (__: any, record: any) => [
        <a
          key="update"
          onClick={() => {
            setMenuFormVisible(true);
            setMenuCurrentRow(record);
          }}
        >
          编辑
        </a>,
        <a
          key="config"
          onClick={async () => {
            setShowMenuEndpointsDetail(true);
            setMenuCurrentRow(record);
          }}
        >
          端点分配
        </a>,
        <Popconfirm key="popconfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(deleteMenu, record.id);
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

  const endponitColumns: ProColumns<EndpointData>[] = [
    {
      title: '端点名称',
      dataIndex: 'label'
    },
    {
      title: '端点路径',
      dataIndex: 'pattern',
    },
    {
      title: '请求方法',
      align: 'center',
      dataIndex: 'method',
      valueType: 'select',
      valueEnum: {
        POST: {
          text: 'POST',
        },
        PUT: {
          text: 'PUT',
        },
        GET: {
          text: 'GET',
        },
        DELETE: {
          text: 'DELETE',
        }
      },
      render: (__, record) => <Tag color={methodMap[record.method].color}>{methodMap[record.method].text}</Tag>,
    }
  ];

  const endponitColumnsWithOption: ProColumns<EndpointData>[] = _.concat(endponitColumns, {
    title: '操作',
    align: 'center',
    key: 'option',
    valueType: 'option',
    render: (__: any, record: any) => [
      <Popconfirm key="popconfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
        onConfirm={async () => {
          await YuCrud.handleDelete(deleteMenuEndpoint, menuCurrentRow?.id, record.id);
          if (menuEndpointsActionRef.current) {
            menuEndpointsActionRef.current.reload();
          }
        }}
      >
        <a key="delete" href="#">
          删除
        </a>
      </Popconfirm>
    ],
  });

  return (
    <PageContainer>
      <ProTable<MenuData>
        columns={columns}
        actionRef={menuActionRef}
        request={queryMenu}
        footer={() => ''}
        postData={(dataList) => {
          setMenuDataList(dataList)
          return dataList;
        }}
        rowKey="id"
        pagination={false}
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
          initialValues={menuCurrentRow || {type: MenuTypeEnum.FOLDER}}
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
      <Drawer
        title={menuCurrentRow?.name}
        width={800}
        visible={showMenuEndpointsDetail}
        onClose={() => {
          setShowMenuEndpointsDetail(false);
        }}
        closable={false}
      >
        {
          showMenuEndpointsDetail &&
          <ProTable<EndpointData>
            actionRef={menuEndpointsActionRef}
            columns={endponitColumnsWithOption}
            params={{
              menuId: menuCurrentRow?.id,
            }}
            request={queryMenuEndponits}
            onLoad={(datasorce) => {
              setCurrentMenuEndpointIds(datasorce.map(item => item.id))
            }}
            rowKey="id"
            pagination={{
              showQuickJumper: true,
            }}
            search={false}
            dateFormatter="string"
            headerTitle={menuCurrentRow?.name}
            toolBarRender={() => [
              <Button type="primary" key="primary"
                onClick={() => {
                  //dictItemFormRef?.current?.resetFields();
                  setShowEndpointsModal(true);
                  //handleDictItemVisible(true);
                }}
              >
                <PlusOutlined /> 新建
              </Button>,
            ]}
          />
        }
      </Drawer>
      {showEndpointsModal &&
        <Modal title="分配端点" visible={showEndpointsModal}
          onOk={() => {
            saveMenuEndpoints(menuCurrentRow?.id as string, selectedEndpoints as string[]).then(() => {
              setSelectedEndpoints(undefined);
              menuEndpointsActionRef.current?.reload()
              setShowEndpointsModal(false);
            })
          }}
          onCancel={() => {
            setSelectedEndpoints(undefined);
            setShowEndpointsModal(false);
          }}
        >

          <ProTable<EndpointData>
            columns={endponitColumns}
            rowSelection={{
              // 自定义选择项参考: https://ant.design/components/table-cn/#components-table-demo-row-selection-custom
              // 注释该行则默认不显示下拉选项
              //selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
              selectedRowKeys: selectedEndpoints,
              //defaultSelectedRowKeys: currentMenuEndpointIds,
              onChange: (selectedRowKeys) => {
                setSelectedEndpoints(selectedRowKeys);
              },
              getCheckboxProps: record => ({
                disabled: currentMenuEndpointIds.indexOf(record.id as string) > -1
              }),
            }}
            params={{
              menuId: menuCurrentRow?.id,
            }}
            request={queryEndponitList}
            rowKey="id"
            pagination={{
              showQuickJumper: true,
            }}
            search={false}
            options={false}
            defaultSize="small"
            dateFormatter="string"
          />

        </Modal>
      }
    </PageContainer>
  );
};

export default MenuPage;