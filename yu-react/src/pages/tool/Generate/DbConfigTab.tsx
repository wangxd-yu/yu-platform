import React, { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';

import type { ApiDomainField } from './data';

import type { EditableFormInstance, ProColumns, ProFormInstance } from '@ant-design/pro-components';
import { EditableProTable, FooterToolbar, ProForm, ProFormSelect, ProFormText } from '@ant-design/pro-components';
import lodash from 'lodash';
import { Switch } from 'antd';
import { request } from '@umijs/max';

interface DbConfigTabProps {
  dataSource?: ApiDomainField[];
  onValuesChange: (values: any) => void;
}

const formLayoutType = 'horizontal';

const convertToApiDomainField = (data) => {
  return data.map((item, index) => ({
    id: item.COLUMN_NAME,
    name: item.COLUMN_NAME,
    type: item.DATA_TYPE,
    length: item.NUMERIC_PRECISION || item.CHARACTER_MAXIMUM_LENGTH,
    decimal: item.NUMERIC_SCALE,
    comment: item.COLUMN_COMMENT,
    sort: item.ORDINAL_POSITION,
    ifNullable: item.IS_NULLABLE === "NO" ? "true" : "",
  }));
};

const defaultData: ApiDomainField[] = [
  {
    id: Date.now().toString() + '_id',
    sort: 1,
    name: `id`,
    type: 'String',
    length: 20,
    comment: '主键',
  },
  {
    id: Date.now().toString() + '_ct',
    sort: 2,
    name: `create_time`,
    type: 'Datetime',
    length: 3,
    comment: '创建时间',
  },
  {
    id: Date.now().toString() + '_ut',
    sort: 3,
    name: `update_time`,
    type: 'Datetime',
    length: 3,
    comment: '更新时间',
  },
];

const DbConfigTab: React.FC<DbConfigTabProps> = forwardRef((prop, ref) => {
  const [tableNameInputType, setTableNameInputType] = useState('text'); // text: 输入框，select: 下拉框 
  const [dataSource, setDataSource] = useState<ApiDomainField[]>(() => defaultData);
  const [editableKeys, setEditableRowKeys] = useState<React.Key[]>(() =>
    defaultData
      .filter((item, i) => {
        return i > 1;
      })
      .map((item) => item.id),
  );
  const formRef = useRef<ProFormInstance>();
  const dbEditorFormRef = useRef<EditableFormInstance<any>>();
  // 使用 useImperativeHandle 暴露 form 实例给父组件
  useImperativeHandle(ref, () => ({
    formRef,
    // 可以添加其他需要暴露的方法
    validate: () => formRef.current?.validateFields(),
  }));
  useEffect(() => {
    // 同步到 form 数据
    formRef.current?.setFieldsValue({
      domainFields: dataSource,
    });
  }, [dataSource]); // 监听 dataSource 变化 

  /**
   * 表单校验规则
   */
  const handleValidator = (_rule: any, value: string | undefined) => {
   // if(validateAll) {//统一校验
      // 过滤出所有name等于value的条目，排除当前正在编辑的字段
      const matchingFields = formRef.current?.getFieldValue('domainFields').filter((item) => item.name === value);
        
      // 如果匹配的条目数大于 1，则说明字段名称重复
      if (matchingFields.length > 1) {
        return Promise.reject('字段名称不能重复');
      }
    /* } else {
      const isnameDuplicate = formRef.current?.getFieldValue('domainFields').some((item) => item.name === value);
      if (isnameDuplicate) {
        return Promise.reject('字段名称不能重复');
      }
    } */
    
    return Promise.resolve();
  };

  /**
   * 上移 数据库属性
   * @param row
   * @returns
   */
  const moveUp = (row: ApiDomainField) => {
    
    // 如果是第一个
    if (row.sort === lodash.min(dataSource.map((item) => item.sort))) {
      return;
    } else {
      const upRow = dataSource.find((item) => item.sort === row.sort - 1);
      const currentRow = dataSource.find((item) => item.id === row.id);
      if (upRow) {
        upRow.sort++;
      }
      if (currentRow) {
        currentRow.sort--;
      }
       // 更新数据源
      const sortedData = dataSource.toSorted((a, b) => a.sort - b.sort);
      setDataSource(sortedData);  // 直接更新 dataSource
    }
  };

  /**
   * 下移 数据库属性
   * @param row
   * @returns
   */
  const moveDown = (row: ApiDomainField) => {
    // 如果是最后一个
    if (row.sort === lodash.max(dataSource.map((item) => item.sort))) {
      return;
    } else {
      const upRow = dataSource.find((item) => item.sort === row.sort + 1);
      const currentRow = dataSource.find((item) => item.id === row.id);
      if (upRow) {
        upRow.sort--;
      }
      if (currentRow) {
        currentRow.sort++;
      }
      // 更新数据源
      const sortedData = dataSource.toSorted((a, b) => a.sort - b.sort);
      setDataSource(sortedData);  // 直接更新 dataSource
    }
  };

  /**
   * 禁用样式
   * @param row
   * @returns
   */
  const disableStyle = {
    color: 'rgba(0,0,0,.25)',
    cursor: 'not-allowed',
  };

  const columns: ProColumns<ApiDomainField>[] = [
    {
      title: '字段名称',
      dataIndex: 'name',
      formItemProps: {
        rules: [
          {
            required: true,
            whitespace: false,
            message: '此项是必填项',
          },
          {
            validator: handleValidator,
          },
        ],
      },
    },
    {
      title: '字段类型',
      key: 'type',
      dataIndex: 'type',
      valueType: 'select',
      valueEnum: {
        String: { text: 'String' },
        Integer: { text: 'Integer' },
        Datetime: { text: 'Datetime' },
      },
    },
    {
      title: '字段长度',
      dataIndex: 'length',
      valueType: 'digit',
      width: 100,
    },
    {
      title: '小数点',
      dataIndex: 'commonDecimal',
      valueType: 'digit',
      width: 100,
    },
    {
      title: '排序',
      width: 50,
      dataIndex: 'sort',
      valueType: 'digit',
      editable: false,
    },
    {
      title: '不是null',
      dataIndex: 'ifNullable',
      valueType: 'checkbox',
      fieldProps: {
        options: [{ label: '', value: 'true' }],
      },
    },
    {
      title: '字段注释',
      dataIndex: 'comment',
    },
    {
      title: '操作',
      valueType: 'option',
      render: (text, record, __, action) => [
        <a
          key="up"
          onClick={() => {
            moveUp(record);
          }}
          style={record.sort === lodash.min(dataSource.map((item) => item.sort)) ? disableStyle : {}}
        >
          上移
        </a>,
        <a
          key="down"
          onClick={() => {
            moveDown(record);
          }}
          style={record.sort === lodash.max(dataSource.map((item) => item.sort)) ? disableStyle : {}}
        >
          下移
        </a>,
        <a
          key="delete"
          style={record.name === 'id' ? disableStyle : {}}
          onClick={() => {
            if (dataSource.filter((item) => item.name !== 'id')) {
              setDataSource(dataSource.filter((item) => item.id !== record.id));
            }
          }}
        >
          删除
        </a>
      ],
    },
  ];
  return (
    <>
      <ProForm<{
        name: string;
        company?: string;
        useMode?: string;
      }>
        initialValues={{
          domainName: '',
          tableName: '',
          domainFields: defaultData, // 这里设置初始的 domainFields 数据
        }}
        formRef={formRef}
        onFinish={async (values) => {
          console.log("onFinish", values);
        }
        }
        layout={formLayoutType}
        submitter={{
          render: (__, dom) => <FooterToolbar style={{ width: '100%' }}>{dom}</FooterToolbar>,
        }}
        grid
      >
        <ProFormText name="domainName" label="名称" placeholder="请输入名称" colProps={{ xs: 12 }} />

        {
          tableNameInputType === 'input' ?
            <ProFormText name="tableName" label="输入表名" placeholder="请输入表名" colProps={{ xs: 8 }} />
            :
            <ProFormSelect
              showSearch  // 启用筛选功能
              fieldProps={{
                //labelInValue: true,
              }}
              onChange={(value) => {
                console.log('value', value);
                request('mysql/columns', {
                  method: 'GET',
                  params: {
                    tableName: value,
                  }
                }).then(data => {
                  //console.log('data', data, convertToApiDomainField(data))
                  const convertData = convertToApiDomainField(data);
                  setDataSource(convertData);
                  setEditableRowKeys(convertData.map((item: any) => item.id));
                  //dbEditorFormRef.current?.setFields(convertToApiDomainField(data));
                });
              }}
              colProps={{ xs: 8 }}
              request={async () => {
                try {
                  const response = await request('mysql/tables', {
                    method: 'GET',
                  });
                  //console.log('获取表格数据成功:', response);
                  const value = response.map((item: {
                    TABLE_COMMENT: any; TABLE_NAME: any;
                  }) => ({
                    label: item.TABLE_NAME + (item.TABLE_COMMENT ? `(${item.TABLE_COMMENT})` : ''),
                    value: item.TABLE_NAME,
                  }))
                  return value;
                } catch (error) {
                  console.error('获取表格数据失败:', error);
                  return []; // 返回空数组以防止错误
                }
              }}
              name="tableName"
              valuePropName="value"  // 指定返回值为 value
              label="选择数据表"
            />
        }

        <Switch
          checkedChildren="数据库"
          unCheckedChildren="输入"
          defaultChecked
          onChange={(checked) => {
            setTableNameInputType(checked ? 'select' : 'input'); // 根据 Switch 状态更新 tableNameInputType
          }}
        />
        {/*  <ProFormSwitch name="switch" checkedChildren="数据库" unCheckedChildren="输入" colProps={{ xs: 4 }} /> */}
        {/* <ProForm.Item name="domainFields" initialValue={[]} trigger="onValuesChange" wrapperCol={{ span: 24 }}> */}
          <EditableProTable<ApiDomainField>
            ghost
            name="domainFields"
            editableFormRef={dbEditorFormRef}
            defaultSize="small"
            columns={columns}
            rowKey="id"
            /*scroll={{
                        x: 960,
                    }}*/
            value={dataSource}
            //controlled={true}
            recordCreatorProps={{
              newRecordType: 'dataSource',
              record: {
                id: Date.now().toString(),
                name:'',
                sort: dataSource.length > 0 ? lodash.max(dataSource.map((item) => item.sort))! + 1 : 1, // 使用默认值1
                decimal: 0,
              }
            }}
            toolBarRender={() => {
              return [];
            }}
            editable={{
              type: 'multiple',
              editableKeys,
              actionRender: (row, config, defaultDom) => {
                return [
                  <a
                    style={
                      row.sort === lodash.min(dataSource.map((item) => item.sort))
                        ? {
                          color: 'rgba(0,0,0,.25)',
                          cursor: 'not-allowed',
                        }
                        : {}
                    }
                    key="up"
                    onClick={() => {
                      moveUp(row);
                    }}
                  >
                    上移
                  </a>,
                  <a
                    style={
                      row.sort === lodash.max(dataSource.map((item) => item.sort))
                        ? {
                          color: 'rgba(0,0,0,.25)',
                          cursor: 'not-allowed',
                        }
                        : {}
                    }
                    key="down"
                    onClick={() => {
                      moveDown(row);
                    }}
                  >
                    下移
                  </a>,
                  //defaultDom.save,
                  defaultDom.delete || defaultDom.cancel,
                ];
              },
              onValuesChange: (record, recordList) => {
                //setDataSource(recordList);
                //console.log("record", record);
              },
              onChange: setEditableRowKeys,
            }}
          />
       {/*  </ProForm.Item> */}
      </ProForm>
    </>
  );
});

export default DbConfigTab;
