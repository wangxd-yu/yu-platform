import React, { useState, useRef } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { Button, Popconfirm, Switch, Tag } from 'antd';
import type { FormInstance } from 'antd';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { queryEndpoint, getEndpoint, addEndpoint, updateEndpoint, deleteEndpoint, accessEnable, accessDisable, logEnable, logDisable } from './service';
import * as YuCrud from '@/utils/yuCrud';
import type { EndpointData, TableListPagination } from './data';
import EndpointForm from './components/EndpointForm'

export const methodMap = {
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
      dataIndex: 'label',
      sorter: true,
    },
    {
      title: '端点路径',
      dataIndex: 'pattern',
      sorter: true,
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
      title: '权限',
      align: 'center',
      dataIndex: 'accessEnabled',
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
        <Switch key="accessEnabled"
          checked={record.accessEnabled}
          checkedChildren="启用"
          unCheckedChildren="停用"
          onChange={() => {
            if (record.accessEnabled) {
              accessDisable(record.id)
            } else {
              accessEnable(record.id)
            }
            endpointActionRef.current?.reload()
          }} />
      ]
    },
    {
      title: '日志',
      align: 'center',
      dataIndex: 'logEnabled',
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
        <Switch key="logEnabled"
          checked={record.logEnabled}
          checkedChildren="启用"
          unCheckedChildren="停用"
          onChange={() => {
            if (record.logEnabled) {
              logDisable(record.id)
            } else {
              logEnable(record.id)
            }
            endpointActionRef.current?.reload()
          }} />
      ]
    },
    {
      title: '创建时间',
      align: 'center',
      search: false,
      sorter: true,
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
        <Popconfirm key="popconfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(deleteEndpoint, record.id)
            if (endpointActionRef.current) {
              endpointActionRef.current.reload();
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
        initialValues={endpointCurrentRow || { accessEnabled: true, logEnabled: false }}
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