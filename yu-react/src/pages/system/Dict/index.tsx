import { PlusOutlined } from '@ant-design/icons';
import { Button, Drawer, Popconfirm } from 'antd';
import type { FormInstance } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { ModalForm, ProFormText, ProFormDigit } from '@ant-design/pro-form';
import { queryDict, queryDictItem, addDict, addDictItem, updateDict, updateDictItem, deleteDict, deleteDictItem } from './service';
import * as YuCrud from '@/utils/yuCrud';
import type { Dict, DictItem } from './data';
import DictForm1 from './components/DictForm1'

const TableList: React.FC = () => {
  /** 新建窗口的弹窗 */
  const [createDictVisible, handleDictVisible] = useState<boolean>(false);
  const [createDictItemVisible, handleDictItemVisible] = useState<boolean>(false);
  const [showDictItemDetail, setShowDictItemDetail] = useState<boolean>(false);
  const dictFormRef = useRef<FormInstance>();
  const dictItemFormRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const itemActionRef = useRef<ActionType>();
  const [dictCurrentRow, setDictCurrentRow] = useState<Dict>();
  const [dictItemCurrentRow, setDictItemCurrentRow] = useState<DictItem>();

  const onDictFill = (record: any) => {
    dictFormRef?.current?.setFieldsValue(record);
  };
  const onDictItemFill = (record: DictItem) => {
    dictItemFormRef?.current?.setFieldsValue(record);
  };

  const columns: ProColumns<Dict>[] = [
    {
      title: '字典名称',
      dataIndex: 'name'
    },
    {
      title: '字典编号',
      tip: '字典编号是唯一的 key',
      dataIndex: 'code',
      hideInForm: true
    },
    {
      title: '描述',
      dataIndex: 'remark',
      search: false,
      valueType: 'textarea',
    },

    {
      title: '创建时间',
      sorter: true,
      search: false,
      dataIndex: 'createTime',
      valueType: 'dateTime'
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_: any, record: any) => [
        <a
          key="update"
          onClick={() => {
            handleDictVisible(true);
            setDictCurrentRow(record);
            onDictFill(record);
          }}
        >
          编辑
        </a>,
        <a
          key="config"
          onClick={() => {
            setShowDictItemDetail(true);
            setDictCurrentRow(record);
          }}
        >
          字典配置
        </a>,
        <Popconfirm key="popconfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(deleteDict, record.id)
            if (actionRef.current) {
              actionRef.current.reload();
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

  const dictItemColumns: ProColumns<DictItem>[] = [
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
      render: (_: any, record: DictItem) => [
        <a key="updateDictItem"
          onClick={() => {
            handleDictItemVisible(true);
            setDictItemCurrentRow(record);
            onDictItemFill(record);
          }}
        >
          编辑
        </a>,
        <Popconfirm key="popconfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(deleteDictItem, record.id);
            if (itemActionRef.current) {
              itemActionRef.current.reload();
            }
          }}
        >
          <a key="delete" href="#">
            删除
          </a>
        </Popconfirm>
      ],
    },
  ]

  const formItemLayout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 19 },
  };

  return (
    <PageContainer header={{
      breadcrumb: {},
    }}>
      <ProTable<Dict, API.TableListPagination>
        headerTitle="查询表格"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              dictFormRef?.current?.resetFields();
              setDictCurrentRow(undefined)
              handleDictVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={queryDict}
        columns={columns}
      />
      <DictForm1
        title={dictCurrentRow?.id ? '更新字典' : '新建字典'}
        visible={createDictVisible}
        onVisibleChange={handleDictVisible}
        formRef={dictFormRef}
        onFinish={async (value) => {
          const data = { ...dictCurrentRow, ...value };
          let success;
          if (!data.id) {
            success = await YuCrud.handleAdd(data as Dict, addDict);
          } else {
            success = await YuCrud.handleUpdate(data as Dict, updateDict);
          }
          if (success) {
            handleDictVisible(false);
            dictFormRef?.current?.resetFields();
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      />
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
          <ProTable<DictItem>
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
          visible={createDictItemVisible}
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
              handleDictItemVisible(false);
              dictItemFormRef?.current?.resetFields();
              if (itemActionRef.current) {
                itemActionRef.current.reload();
              }
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
          <ProFormDigit label="排序" name="sort" min={1} max={99} />
        </ModalForm>
      </Drawer>
    </PageContainer >
  );
};

export default TableList;
