import React, { useState } from 'react';
import type { ProColumns } from '@ant-design/pro-table';
import type { LogEndpointData } from './data';
import { PageContainer } from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import { getLogEndpoint, queryLogEndpoint } from './service';
import { Button, Drawer } from 'antd';
import ProDescriptions from '@ant-design/pro-descriptions';

const LogEndpointTable: React.FC<LogEndpointData> = () => {
  const [logEndpointDetailVisible, setLogEndpointDetailVisible] = useState<boolean>(false);
  const [logEndpointCurrentRow, setLogEndpointCurrentRow] = useState<LogEndpointData>();

  const columns: ProColumns<LogEndpointData>[] = [
    {
      title: '端点名称',
      dataIndex: 'endpointName'
    },
    {
      title: '端点路径',
      dataIndex: 'pattern'
    },
    {
      title: '登录用户',
      align: 'center',
      dataIndex: 'username',
      sorter: true
    },
    {
      title: 'IP',
      align: 'center',
      dataIndex: 'ip'
    },
    {
      title: '请求方法',
      align: 'center',
      dataIndex: 'method'
    },/* 
    {
      title: '处理器',
      dataIndex: 'handler'
    }, */
    {
      title: '响应状态',
      align: 'center',
      dataIndex: 'httpStatus'
    },
    {
      title: '请求耗时',
      align: 'center',
      sorter: true,
      dataIndex: 'time'
    },
    {
      title: '请求时间',
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
          key="detail"
          onClick={async () => {
            await getLogEndpoint(record.id, setLogEndpointCurrentRow)
            setLogEndpointDetailVisible(true);
          }}
        >
          详情
        </a>
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<LogEndpointData, API.TableListPagination>
        headerTitle="查询表格"
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        request={queryLogEndpoint}
        columns={columns}
      />
      <Drawer
        title={logEndpointCurrentRow?.url}
        width={800}
        visible={logEndpointDetailVisible}
        onClose={() => {
          setLogEndpointDetailVisible(false);
          setLogEndpointCurrentRow(undefined);
        }}
        closable={false}
      >
        <ProDescriptions column={1} bordered>
          <ProDescriptions.Item label="url">
            {logEndpointCurrentRow?.url}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="httpStatus">
            {logEndpointCurrentRow?.httpStatus}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="createTime">
            {logEndpointCurrentRow?.createTime}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="time">
            {logEndpointCurrentRow?.time}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="用户名">
            {logEndpointCurrentRow?.username}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="IP">
            {logEndpointCurrentRow?.ip}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="method">
            {logEndpointCurrentRow?.method}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="handler">
            {logEndpointCurrentRow?.handler}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="pattern">
            {logEndpointCurrentRow?.pattern}
          </ProDescriptions.Item>

          <ProDescriptions.Item label="handler">
            {logEndpointCurrentRow?.handler}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="JSON 代码块" valueType="jsonCode">
            {logEndpointCurrentRow?.request}
          </ProDescriptions.Item>
          <ProDescriptions.Item label="JSON 代码块" valueType="jsonCode">
            {logEndpointCurrentRow?.response}
          </ProDescriptions.Item>
        </ProDescriptions>
      </Drawer>
    </PageContainer >
  );
};
export default LogEndpointTable;