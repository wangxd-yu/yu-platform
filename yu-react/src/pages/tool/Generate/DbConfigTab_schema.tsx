import {
  ArrayTable,
  Editable,
  FormGrid,
  FormItem,
  Input,
  NumberPicker,
  Switch,
} from '@formily/antd-v5';
import { createForm } from '@formily/core';
import { FormProvider, createSchemaField } from '@formily/react';
import { forwardRef, useEffect, useImperativeHandle } from 'react';

const SchemaField = createSchemaField({
  components: {
    FormGrid,
    FormItem,
    Editable,
    Input,
    ArrayTable,
    NumberPicker,
    Switch,
  },
});

const defaultData: any[] = [
  {
    id: Date.now().toString() + '_id',
    sort: 1,
    name: `id`,
    type: 'String',
    length: 20,
    comment: '主键',
    ifNotNull: true,
  },
  {
    id: Date.now().toString() + '_ct',
    sort: 2,
    name: `create_time`,
    type: 'Datetime',
    length: 3,
    comment: '创建时间',
    ifNotNull: false,
  },
  {
    id: Date.now().toString() + '_ut',
    sort: 3,
    name: `update_time`,
    type: 'Datetime',
    length: 3,
    comment: '更新时间',
    ifNotNull: false,
  },
];

const form = createForm({ initialValues: { domainFields: defaultData } });

const schema = {
  type: 'object',
  properties: {
    y7ll3qybo7o: {
      type: 'void',
      'x-component': 'FormGrid',
      'x-designable-id': 'y7ll3qybo7o',
      'x-index': 0,
      properties: {
        domainName: {
          type: 'string',
          title: '名称',
          'x-decorator': 'FormItem',
          'x-component': 'Input',
          name: 'domainName',
          required: true,
          'x-designable-id': 't92rdkyi5d9',
          'x-index': 0,
        },
        tableName: {
          type: 'string',
          title: '表名',
          'x-decorator': 'FormItem',
          'x-component': 'Input',
          name: 'tableName',
          required: true,
          'x-designable-id': 'hq4f17xkx6y',
          'x-index': 1,
        },
      },
    },
    domainFields: {
      type: 'array',
      'x-decorator': 'FormItem',
      'x-component': 'ArrayTable',
      'x-component-props': {
        pagination: {
          pageSize: 10,
        },
        scroll: {
          x: '100%',
        },
      },
      items: {
        type: 'object',
        properties: {
          column1: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '',
              align: 'center',
              width: 50,
            },
            properties: {
              sort: {
                type: 'void',
                'x-component': 'ArrayTable.SortHandle',
              },
            },
          },
          column2: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '排序',
              align: 'center',
              width: 80,
            },
            properties: {
              index: {
                type: 'string',
                'x-decorator': 'FormItem',
                'x-component': 'ArrayTable.Index',
              },
            },
          },
          columnName: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '字段名称',
              style: {
                width: 'auto',
              },
            },
            properties: {
              name: {
                type: 'string',
                'x-decorator': 'FormItem',
                'x-component': 'Input',
                required: true,
              },
            },
          },
          columnType: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '字段类型',
              style: {
                width: 'auto',
              },
            },
            properties: {
              type: {
                type: 'string',
                'x-decorator': 'FormItem',
                'x-component': 'Input',
                required: true,
              },
            },
          },
          columnLength: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '字段长度',
              style: {
                width: 'auto',
              },
            },
            properties: {
              length: {
                type: 'string',
                'x-decorator': 'FormItem',
                'x-component': 'NumberPicker',
                'x-validator': 'integer',
                required: true,
              },
            },
          },
          columnCommonDecimal: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '小数点',
              style: {
                width: 'auto',
              },
            },
            properties: {
              commonDecimal: {
                type: 'string',
                'x-decorator': 'FormItem',
                'x-component': 'NumberPicker',
                'x-validator': 'integer',
              },
            },
          },
          columnIfNullable: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '非空',
              style: {
                width: 'auto',
              },
            },
            properties: {
              ifNotNull: {
                type: 'boolean',
                'x-decorator': 'FormItem',
                'x-component': 'Switch',
              },
            },
          },
          columnComment: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '字段注释',
              style: {
                width: 'auto',
              },
            },
            properties: {
              comment: {
                type: 'string',
                'x-decorator': 'FormItem',
                'x-component': 'Input',
              },
            },
          },
          column6: {
            type: 'void',
            'x-component': 'ArrayTable.Column',
            'x-component-props': {
              title: '操作',
              dataIndex: 'operations',
              fixed: 'right',
            },
            properties: {
              item: {
                type: 'void',
                'x-component': 'FormItem',
                properties: {
                  moveDown: {
                    type: 'void',
                    'x-component': 'ArrayTable.MoveDown',
                  },
                  moveUp: {
                    type: 'void',
                    'x-component': 'ArrayTable.MoveUp',
                  },
                  remove: {
                    type: 'void',
                    'x-component': 'ArrayTable.Remove',
                    'x-reactions': [
                      {
                        dependencies: ['.name'],
                        when: "{{$deps[0] =='id'}}",
                        fulfill: {
                          schema: {
                            'x-visible': false,
                          },
                        },
                        otherwise: {
                          schema: {
                            'x-visible': true,
                          },
                        },
                      },
                    ],
                  },
                },
              },
            },
          },
        },
      },
      properties: {
        add: {
          type: 'void',
          'x-component': 'ArrayTable.Addition',
          title: '添加条目',
        },
      },
    },
  },
};

const DbConfigTab = forwardRef((props, ref) => {
  useEffect(() => {});
  // 使用 useImperativeHandle 暴露 form 实例给父组件
  useImperativeHandle(ref, () => ({
    form,
    // 可以添加其他需要暴露的方法
    getState: () => form.getState(),
  }));
  return (
    <>
      <FormProvider form={form}>
        <SchemaField schema={schema} />
        {/* <FormButtonGroup>
          <Submit onSubmit={() => {
            console.log(form.getFormState());
            console.log(form.getState())
          }}>提交</Submit>
        </FormButtonGroup> */}
      </FormProvider>
    </>
  );
});
export default DbConfigTab;
