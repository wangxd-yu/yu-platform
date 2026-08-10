import YuProTable from '@/components/Yu/YuProTable';
import * as YuCrud from '@/utils/yuCrud';
import * as YuApi from '@/utils/yuApi';
import { PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import type { FormInstance } from 'antd';
import { Button, Popconfirm } from 'antd';
import React, { useRef, useState } from 'react';
import CodeTemplateForm from './components/CodeTemplateForm';
import type { CodeTemplate } from './data';

import { addCodeTemplate, deleteCodeTemplate, queryCodeTemplate, updateCodeTemplate } from './service';

const CodeTemplateTable: React.FC<CodeTemplate> = () => {
  const [showForm, setShowForm] = useState<boolean>(false);
  const [currentRow, setCurrentRow] = useState<CodeTemplate>();
  const formRef = useRef<FormInstance>();
  const tableActionRef = useRef<ActionType>();

  const columns: ProColumns<CodeTemplate>[] = [
    {
      title: '主键',
      align: 'center',
      dataIndex: 'id',
      search: false,
    },
    {
      title: '模板名称',
      align: 'center',
      dataIndex: 'name'
    },
    {
      title: '创建时间',
      align: 'center',
      dataIndex: 'createTime',
      search: false,
    },
    {
      title: '更新时间',
      align: 'center',
      dataIndex: 'updateTime',
      search: false,
    },
    {
      title: '操作',
      align: 'center',
      dataIndex: 'option',
      valueType: 'option',
      render: (_: any, record: any) => [
        <a
          key="update"
          onClick={async() => {
            const template = await YuApi.get('/genCodeTemplate/' + record.id) as CodeTemplate;
            setCurrentRow(template);
            setShowForm(true);
          }}
        >
          编辑
        </a>,
        <Popconfirm
          key="deleteConfirm"
          title={`确认删除该记录吗?`}
          okText="是"
          cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(deleteCodeTemplate, record.id);
            if (tableActionRef.current) {
              tableActionRef.current.reload();
            }
          }}
        >
          <a key="delete" href="#">
            删除
          </a>
        </Popconfirm>,
      ],
    },
  ];

  return (
    <>
      <YuProTable<CodeTemplate, API.TableListPagination>
        showDeptTree={false}
        tableProps={{
          actionRef: tableActionRef,
          columns: columns,
          /* defaultData: [{ id: '1', content: '1111111' }], */
          request: queryCodeTemplate,
          toolBarRender: () => [
            <Button
              type="primary"
              key="primary"
              onClick={() => {
                formRef?.current?.resetFields();
                setCurrentRow(undefined);
                setShowForm(true);
              }}
            >
              <PlusOutlined />
              新建
            </Button>,
          ],
        }}
      />
      {
        currentRow?.code && <CodeTemplateForm
        width="100%"
        formType={'DrawerForm'}
        title={currentRow?.id ? '更新代码模板' : '新建代码模板'}
        open={showForm}
        onOpenChange={(visible) => {
          if (visible) {
            formRef?.current?.resetFields();
          } else {
            setShowForm(false);
            setCurrentRow(undefined);
          }
        }}
        formRef={formRef}
        initialValues={currentRow || {}}
        onFinish={async (value) => {
          const data = { ...currentRow, ...value };
          let success;
          if (!data.id) {
            success = await YuCrud.handleAdd(data as CodeTemplate, addCodeTemplate);
          } else {
            success = await YuCrud.handleUpdate(data as CodeTemplate, updateCodeTemplate);
          }
          if (success) {
            setShowForm(false);
            formRef?.current?.resetFields();
            if (tableActionRef.current) {
              tableActionRef.current.reload();
            }
          }
        }}
      />
      }
      
    </>
  );
};

export default CodeTemplateTable;
