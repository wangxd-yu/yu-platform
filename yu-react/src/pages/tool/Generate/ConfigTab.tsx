import { Button, Form, Modal } from 'antd';
import lodash from 'lodash';
import React, { useImperativeHandle, useRef, useState } from 'react';
import TableTransfer from './TableTransfer';

import type { ProColumns, ProFormInstance } from '@ant-design/pro-components';
import { EditableProTable, ProForm } from '@ant-design/pro-components';
import type { ApiDomainField } from './data';

// 要求包含 sort 属性的泛型类型
interface Sortable {
  id: string;
  sort: number;
}

interface ConfigTabProps<T extends Sortable> {
  onValuesChange: (values: any) => void;
  children?: React.ReactNode;
  transferDataSource: ApiDomainField[];
  columns: ProColumns<any>[];
  mapApiDomainFieldToT: (apiDomainField: ApiDomainField) => T;
}

const leftColumns = [
  {
    title: '字段名称',
    dataIndex: 'name',
  },
  {
    title: '字段注释',
    dataIndex: 'comment',
  },
];

const rightColumns = [
  {
    title: '字段名称',
    dataIndex: 'name',
  },
  {
    title: '字段注释',
    dataIndex: 'comment',
  },
];

/**
 * 下划线转换驼峰
 * @param name
 * @returns
 */
const toHump = (name: string) => {
  if (name) {
    return name.replace(/\_(\w)/g, function (all, letter) {
      return letter.toUpperCase();
    });
  } else {
    return name;
  }
};

const ConfigTab: React.ForwardRefRenderFunction<
  { validate: () => any } | null,
  ConfigTabProps<Sortable>
> = (props, ref) => {
  const { transferDataSource } = props;
  const formRef = useRef<ProFormInstance>();
  const [tableRef] = Form.useForm();

  const [formDataSource, setFormDataSource] = useState<Sortable[] | undefined>(undefined); // 这里使用 T
  const [editableKeys, setEditableRowKeys] = useState<React.Key[]>();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [targetKeys, setTargetKeys] = useState<string[]>();

  // 使用 useImperativeHandle 暴露 form 实例给父组件
  useImperativeHandle(ref, () => ({
    // 可以添加其他需要暴露的方法
    validate: async () => {
      try {
        // 手动触发 EditableProTable 的校验
        await tableRef.validateFields();
        await formRef.current?.validateFields();
        // 在这里可以执行表单提交等操作
        console.log('Form validation passed');
        console.log('Form data:', formDataSource);
        return { success: true, data: formDataSource }; // 校验通过
      } catch (errorInfo) {
        // 校验失败，可以根据需要进行处理
        console.log('Form validation failed:', errorInfo);
        return { success: false }; // 校验失败
      }
    },
  }));

  const onFormTransferChange = (nextTargetKeys: string[]) => {
    setTargetKeys(nextTargetKeys);
  };

  const showModal = () => {
    setIsModalOpen(true);
  };

  /**
   * 上移 数据库属性
   * @param row
   * @returns
   */
  const moveUp = <T extends Sortable>(row: T) => {
    if (formDataSource) {
      // 如果是第一个
      if (row.sort === lodash.min(formDataSource.map((item) => item.sort))) {
        return;
      } else {
        const upRow = formDataSource.find((item) => item.sort === row.sort - 1);
        const currentRow = formDataSource.find((item) => item.id === row.id);
        if (upRow) {
          upRow.sort++;
        }
        if (currentRow) {
          currentRow.sort--;
        }
        setFormDataSource(
          formDataSource.toSorted((a, b) => {
            return a.sort - b.sort;
          }),
        );
      }
    }
  };

  /**
   * 下移 数据库属性
   * @param row
   * @returns
   */
  const moveDown = <T extends Sortable>(row: T) => {
    if (formDataSource) {
      // 如果是最后一个
      if (row.sort === lodash.max(formDataSource.map((item) => item.sort))) {
        return;
      } else {
        const upRow = formDataSource.find((item) => item.sort === row.sort + 1);
        const currentRow = formDataSource.find((item) => item.id === row.id);
        if (upRow) {
          upRow.sort--;
        }
        if (currentRow) {
          currentRow.sort++;
        }
        setFormDataSource(formDataSource.toSorted((a, b) => a.sort - b.sort));
      }
    }
  };

  const handleOk = () => {
    const formColumns = transferDataSource
      .filter((item) => targetKeys?.includes(item.key as string))
      .map((item) => {
        return props.mapApiDomainFieldToT(item);
        /*return {
                id: item.id,
                field: item.name,
                title: item.comment,
                length: item.length,
                sort: item.sort
            }*/
      });
    setFormDataSource(formColumns);
    setEditableRowKeys(targetKeys);
    setIsModalOpen(false);

    console.log('formRef.current?.getFieldsValue()', formRef.current?.getFieldsValue());
    props.onValuesChange(formRef.current?.getFieldsValue());
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };
  return (
    <>
      <ProForm
        grid
        formRef={formRef}
        submitter={{
          //render: (_, dom) => <FooterToolbar style={{ width: '100%' }}>{dom}</FooterToolbar>,
          render: () => <></>,
        }}
        /* onFinish={async (values) => {
            console.log(values);
            try {
              // 手动触发 EditableProTable 的校验
              await tableRef.validateFields();
              await formRef.current?.validateFields();
              // 在这里可以执行表单提交等操作
              console.log('Form validation passed');
            } catch (errorInfo) {
              // 校验失败，可以根据需要进行处理
              console.log('Form validation failed:', errorInfo);
            }
          }} */
      >
        {props.children}

        <ProForm.Item
          name="columns"
          initialValue={[]}
          /* trigger="onValuesChange"
            rules={[
              {
                validator: () => {
                  try {
                    tableRef.validateFields();
                    return Promise.resolve(true);
                  } catch {
                    return Promise.reject(false);
                  }
                },
              },
            ]}
            validateTrigger="none" */
        >
          <EditableProTable<Sortable>
            size="small"
            ghost
            rowKey="id"
            /*toolBarRender={false}*/
            value={formDataSource}
            // 关闭默认的新建按钮
            recordCreatorProps={false}
            columns={props.columns}
            toolBarRender={() => {
              return [
                <Button type="primary" key="save" onClick={showModal}>
                  属性维护
                </Button>,
              ];
            }}
            editable={{
              form: tableRef,
              type: 'multiple',
              editableKeys,
              actionRender: (row, config, defaultDom) => {
                return [
                  <a
                    style={
                      row.sort === lodash.min(formDataSource?.map((item) => item.sort))
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
                      row.sort === lodash.max(formDataSource?.map((item) => item.sort))
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
              /* onValuesChange: (record, recordList) => {
                  setFormDataSource(recordList);
                  console.log('formRef.current', formRef.current);
                  setTargetKeys(recordList.map((item) => item.id));
                }, */
              onChange: setEditableRowKeys,
            }}
          />
        </ProForm.Item>
      </ProForm>

      <Modal
        title="Basic Modal"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        width={1000}
      >
        <TableTransfer
          dataSource={transferDataSource.map((item) => {
            item.key = item.id;
            item.name = toHump(item.name);
            return item;
          })}
          leftColumns={leftColumns}
          rightColumns={rightColumns}
          targetKeys={targetKeys}
          onChange={onFormTransferChange}
        />
      </Modal>
    </>
  );
};

export default React.forwardRef(ConfigTab);
