import React, { useState, useRef } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { Button, Switch, Tag } from 'antd';
import type { FormInstance } from 'antd';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { queryEndpoint,getEndpoint, addEndpoint, updateEndpoint, deleteEndpoint } from './service';
import * as YuCrud from '@/utils/yuCrud';
import type { EndpointData, TableListPagination } from './data';
import EndpointForm from './components/EndpointForm'

const methodMap = {
  POST: {
    color: 'blue',
    text: 'POST',
  },
  PUT: {
    color: 'green',
    text: 'PUT',
  },
  GET: {
    color: 'volcano',
    text: 'GET',
  },
  DELETE: {
    color: 'red',
    text: 'DELETE',
  }
};

const EndpointTable: React.FC<EndpointData> = () => {
  /** 新建窗口的弹窗 */
  const [endpointFormVisible, setEndpointFormVisible] = useState<boolean>(false);
  const [endpointCurrentRow, setEndpointCurrentRow] = useState<EndpointData>();
  const endpointFormRef = useRef<FormInstance>();
  const endpointActionRef = useRef<ActionType>();

  const columns: ProColumns<EndpointData>[] = [
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
      render: (_, record) => <Tag color={methodMap[record.method].color}>{methodMap[record.method].text}</Tag>,
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
        <Switch key="enabled" checked={record.enabled} checkedChildren="启用" unCheckedChildren="停用" />
      ]
    },
    {
      title: '创建时间',
      align: 'center',
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
            await getEndpoint(record.id, setEndpointCurrentRow)
            setEndpointFormVisible(true);
          }}
        >
          编辑
        </a>,
        <a key="subscribeAlert"
          onClick={async () => {
            await YuCrud.handleDelete(record.id, deleteEndpoint)
            if (endpointActionRef.current) {
              endpointActionRef.current.reload();
            }
          }}>
          删除
        </a>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<EndpointData, TableListPagination>
        headerTitle="查询表格"
        actionRef={endpointActionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              endpointFormRef?.current?.resetFields();
              setEndpointCurrentRow(undefined)
              setEndpointFormVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={queryEndpoint}
        columns={columns}
      />
      <EndpointForm
        width="500px"
        title={endpointCurrentRow?.id ? '更新端点' : '新建端点'}
        visible={endpointFormVisible}
        onVisibleChange={(visible) => {
          if (visible) {
            endpointFormRef?.current?.resetFields();
          } else {
            setEndpointFormVisible(false);
            setEndpointCurrentRow(undefined);
          }
        }}
        formRef={endpointFormRef}
        initialValues={endpointCurrentRow || {}}
        onFinish={async (value) => {
          const data = { ...endpointCurrentRow, ...value };
          let success;
          if (!data.id) {
            success = await YuCrud.handleAdd(data as EndpointData, addEndpoint);
          } else {
            success = await YuCrud.handleUpdate(data as EndpointData, updateEndpoint);
          }
          if (success) {
            setEndpointFormVisible(false);
            endpointFormRef?.current?.resetFields();
            if (endpointActionRef.current) {
              endpointActionRef.current.reload();
            }
          }
        }}
      />
    </PageContainer >
  );
};
export default EndpointTable;