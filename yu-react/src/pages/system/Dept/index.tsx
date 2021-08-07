import React, { useState, useRef } from 'react';
import type { FormInstance } from 'antd';
import { Switch, Button, Popconfirm } from 'antd';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import type { DeptData } from './data';
import { PlusOutlined } from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import * as YuCrud from '@/utils/yuCrud';
import { queryDept, addDept, updateDept, deleteDept } from './service'
import DeptForm from './components/DeptForm'

const DeptPage: React.FC = () => {
  const [deptFormVisible, setDeptFormVisible] = useState<boolean>(false);
  const [deptCurrentRow, setDeptCurrentRow] = useState<DeptData>();
  const [deptDataList, setDeptDataList] = useState<DeptData[]>();
  const deptFormRef = useRef<FormInstance>();
  const deptActionRef = useRef<ActionType>();

  const columns: ProColumns<DeptData>[] = [
    {
      title: '名称',
      dataIndex: 'name'
    },
    {
      title: '类型',
      align: 'center',
      dataIndex: 'typeCode'
    },
    {
      title: '排序',
      align: 'center',
      dataIndex: 'sort'
    },
    {
      title: '状态',
      align: 'center',
      dataIndex: 'enabled',
      render: (_: any, record: any) => [
        <Switch key="enabled" checked={record.enabled} checkedChildren="启用" unCheckedChildren="停用" />
      ]
    },
    {
      title: '创建时间',
      align: 'center',
      dataIndex: 'createTime'
    },
    {
      title: '操作',
      key: 'option',
      align: 'center',
      valueType: 'option',
      render: (_: any, record: any) => [
        <a
          key="update"
          onClick={() => {
            setDeptFormVisible(true);
            setDeptCurrentRow(record);
          }}
        >
          编辑
        </a>,
        <a
          key="addSub"
          onClick={() => {
            deptFormRef?.current?.resetFields();
            setDeptCurrentRow({
              pno: record.no,
              pName: record.name,
              typeId: record.typeId
            })
            setDeptFormVisible(true);
          }}
        >
          添加下级
        </a>,
        <Popconfirm key="popconfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(record.id, deleteDept);
            if (deptActionRef.current) {
              deptActionRef.current.reload();
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
      <ProTable<DeptData>
        columns={columns}
        actionRef={deptActionRef}
        request={queryDept}
        postData={(dataList) => {
          setDeptDataList(dataList)
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
        headerTitle="部门管理"
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              deptFormRef?.current?.resetFields();
              setDeptCurrentRow(undefined)
              setDeptFormVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
      />
      {deptDataList &&
        <DeptForm
          formRef={deptFormRef}
          visible={deptFormVisible}
          initialValues={deptCurrentRow || {}}
          isAdd={!deptCurrentRow?.id}
          deptDataList={deptDataList}
          onVisibleChange={(visible) => {
            if (visible) {
              deptFormRef?.current?.resetFields();
            } else {
              setDeptFormVisible(false);
              setDeptCurrentRow(undefined);
            }
          }}
          onFinish={async (value) => {
            const data = { ...deptCurrentRow, ...value };
            let success;
            if (!data.id) {
              success = await YuCrud.handleAdd(data as DeptData, addDept);
            } else {
              success = await YuCrud.handleUpdate(data as DeptData, updateDept);
            }
            if (success) {
              setDeptFormVisible(false);
              deptFormRef?.current?.resetFields();
              if (deptActionRef.current) {
                deptActionRef.current.reload();
              }
            }
          }}
        />
      }
    </PageContainer>
  );
};

export default DeptPage;