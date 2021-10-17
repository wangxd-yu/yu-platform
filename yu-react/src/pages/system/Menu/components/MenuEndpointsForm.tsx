import React, { useState, useEffect, useRef } from 'react'
import { ModalForm, ProFormDependency, ProFormSelect, ProFormSwitch, ProFormText } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import { yuUrlSystem } from '@/utils/yuUrl';
import * as YuApi from '@/utils/yuApi';
import _ from 'lodash'
import type { FormInstance } from 'antd';
import { Button, Drawer } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';

type EndpointLessDTO = {
  pattern: string, 
  method: string
}

const dictItemColumns: ProColumns<any>[] = [
  {
    title: '字典标签',
    dataIndex: 'name',
    hideInForm: true
  },
  {
    title: '字典值',
    dataIndex: 'code',
    hideInForm: true
  },
  {
    title: '排序',
    dataIndex: 'sort'
  },
  {
    title: '操作',
    dataIndex: 'option',
    valueType: 'option',
    render: (_: any, record: any) => [
      <a key="updateDictItem"
        onClick={() => {
        }}
      >
        编辑
      </a>,
      <a key="deleteDictItem"
        onClick={async () => {
        }}>
        删除
      </a>,
    ],
  },
]

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const MenuEndpointsForm: React.FC<YuFormProps> = (props: YuFormProps) => {
  const [endpointList, setEndpointList] = useState<Map<string,EndpointLessDTO[]>>(new Map());
  const [createDictItemVisible, handleDictItemVisible] = useState<boolean>(false);
  const [dictCurrentRow, setDictCurrentRow] = useState<any>();
  const [dictItemCurrentRow, setDictItemCurrentRow] = useState<any>();
  const [showDictItemDetail, setShowDictItemDetail] = useState<boolean>(false);

  const itemActionRef = useRef<ActionType>();
  const dictItemFormRef = useRef<FormInstance>();

  useEffect(() => {
    YuApi.get<EndpointLessDTO>(yuUrlSystem('/endpoint/all')).then(res => {
      setEndpointList(new Map(Object.entries<EndpointLessDTO[]>(_.groupBy(res, 'pattern') as unknown as {pattern: EndpointLessDTO[]})))
    });
  }, []);

  return (
    <Drawer
    title={dictCurrentRow?.name}
    width={800}
    visible={showDictItemDetail}
    onClose={() => {
      setShowDictItemDetail(false);
      setDictCurrentRow(undefined);
    }}
    closable={false}
  >
    {
      showDictItemDetail &&
      <ProTable
        actionRef={itemActionRef}
        columns={dictItemColumns}
        params={{
          dictId: dictCurrentRow?.id,
        }}
        request={queryDictItem}
        rowKey="id"
        pagination={{
          showQuickJumper: true,
        }}
        search={false}
        dateFormatter="string"
        headerTitle={dictCurrentRow?.name}
        toolBarRender={() => [
          <Button type="primary" key="primary"
            onClick={() => {
              dictItemFormRef?.current?.resetFields();
              setDictItemCurrentRow(undefined);
              handleDictItemVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
      />
    }
    <ModalForm
      title="新建字典值"
      width="500px"
      {...formItemLayout}
      formRef={dictItemFormRef}
      visible={true}
      layout='horizontal'
      onVisibleChange={handleDictItemVisible}
      onFinish={async (value) => {
        const data = { dictId: dictCurrentRow?.id, ...dictItemCurrentRow, ...value };
        let success;
        if (!data.id) {
          success = await YuCrud.handleAdd(data as DictItem, addDictItem);
        } else {
          success = await YuCrud.handleUpdate(data as DictItem, updateDictItem);
        }
        if (success) {
        }
      }}
    >
      <ProFormText
        rules={[
          {
            required: true,
            message: '字典标签为必填项',
          },
        ]}
        label="字典标签"
        name="name"
      />
      <ProFormText
        rules={[
          {
            required: true,
            message: '字典值为必填项',
          },
        ]}
        label="字典值"
        name="code"
      />
    </ModalForm>
  </Drawer>
  )
}
export default MenuEndpointsForm;