import React from 'react';
import type { ProColumns } from '@ant-design/pro-table';
import type { LogLoginData } from './data';
import { PageContainer } from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import { queryLogLogin } from './service';

const EndpointTable: React.FC<LogLoginData> = () => {
  
    const columns: ProColumns<LogLoginData>[] = [
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
        title: '登录地址',
        align: 'center',
        dataIndex: 'location'
      },
      {
        title: '浏览器',
        align: 'center',
        dataIndex: 'browser'
      },
      {
        title: '操作系统',
        align: 'center',
        dataIndex: 'os'
      },
      {
        title: '登录时间',
        align: 'center',
        search: false,
        sorter: true,
        dataIndex: 'createTime',
        valueType: 'dateTime'
      }
    ];
  
    return (
      <PageContainer>
        <ProTable<LogLoginData, API.TableListPagination>
          headerTitle="查询表格"
          rowKey="id"
          search={{
            labelWidth: 120,
          }}
          request={queryLogLogin}
          columns={columns}
        />
      </PageContainer >
    );
  };
  export default EndpointTable;