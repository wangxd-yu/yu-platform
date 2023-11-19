<#--定义业务类型-->
<#assign moduleType = module.genViewModuleDO.moduleName?cap_first>
<#--定义业务对象-->
<#assign moduleObject = module.genViewModuleDO.moduleName?uncap_first>
<#--定义业务类型-->
<#assign tableDO = module.genViewModuleDO.genViewCrudDO.genViewTableDO>
import React, { useRef, useState } from 'react';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import type { ${moduleType} } from './data';
import { add${moduleType}, delete${moduleType}, query${moduleType}, update${moduleType} } from './service';
import YuProTable from '@/components/Yu/YuProTable';
import { Button, FormInstance, Popconfirm } from 'antd';
import ${moduleType}Form from './components/${moduleType}Form';
import * as YuCrud from '@/utils/yuCrud';
import { PlusOutlined } from '@ant-design/icons';
import * as YuApi from '@/utils/yuApi';

const ${moduleType}Table: React.FC<${moduleType}> = () => {
const [showForm, setShowForm] = useState
<boolean>(false);
  const [currentRow, setCurrentRow] = useState<${moduleType}>();
  const formRef = useRef
  <FormInstance>();
    const tableActionRef = useRef
    <ActionType>();

      const columns: ProColumns<${moduleType}>[] = [
      <#if tableDO.columns??>
        <#list tableDO.columns as column>
          {
          title: '${column.title}',
          align: 'center',
          dataIndex: '${column.field}',
          search: ${column.search?c},
          },
        </#list>
      </#if>
    {
      title: '操作',
      align: 'center',
      dataIndex: 'option',
      valueType: 'option',
      render: (_: any, record: any) => [
      <a
              key="update"
              onClick={() => {
      setCurrentRow(record);
      setShowForm(true);
      }}
      >
      编辑
      </a>,
      <Popconfirm key="deleteConfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
                  onConfirm={async ()=> {
        await YuCrud.handleDelete(delete${moduleType}, record.id)
        if (tableActionRef.current) {
        tableActionRef.current.reload();
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
      <>
      <YuProTable
      <${moduleType}, API.TableListPagination>
      showDeptTree={false}
      tableProps={{
      actionRef: tableActionRef,
      columns: columns,
      <#--scroll: {  x: '2500px' },-->
      request: query${moduleType},
      toolBarRender: () => [
      <Button
              type="primary"
              key="primary"
              onClick={()
      => {
      formRef?.current?.resetFields();
      setCurrentRow(undefined)
      setShowForm(true);
      }}
      >
      <PlusOutlined/>
      新建
      </Button>
      ]
      }}
      />
      <${moduleType}Form
              width="700px"
              formType={'DrawerForm'}
              title={currentRow?.id ?
      '更新${tableDO.title}' : '新建${tableDO.title}'}
      visible={showForm}
      onVisibleChange={(visible) => {
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
      success = await YuCrud.handleAdd(data as ${moduleType}, add${moduleType});
      } else {
      success = await YuCrud.handleUpdate(data as ${moduleType}, update${moduleType});
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
    </
    >
    );
    };
    export default ${moduleType}Table;