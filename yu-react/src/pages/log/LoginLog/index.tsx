import React, { } from 'react';
import type { ProColumns } from '@ant-design/pro-table';
import type { LogLoginData } from './data';
import { queryLogLogin } from './service';
import YuProTable from '@/components/Yu/YuProTable';

const EndpointTable: React.FC<LogLoginData> = () => {

  const columns: ProColumns<LogLoginData>[] = [
    {
      title: '登录用户',
      align: 'center',
      dataIndex: 'username',
      sorter: true
    },
    {
      title: '所属部门',
      dataIndex: 'deptName',
      search: false,
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
    <YuProTable<LogLoginData, API.TableListPagination>
      tableProps={{
        columns: columns,
        request: queryLogLogin,
      }}
    />
  );
};
export default EndpointTable;
