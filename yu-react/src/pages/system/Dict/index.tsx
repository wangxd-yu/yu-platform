import { PlusOutlined } from '@ant-design/icons';
import { Button, message, Drawer } from 'antd';
import type { FormInstance } from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { ModalForm, ProFormText, ProFormTextArea, ProFormDigit } from '@ant-design/pro-form';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import { dict, addDict, updateDict, removeDict, dictItem } from './service';
import type { TableListItem, TableListPagination, DictItem } from './data';

/**
 * 添加节点
 *
 * @param fields
 */
const handleAdd = async (fields: TableListItem) => {
  const hide = message.loading('正在添加');

  try {
    await addDict({ ...fields });
    hide();
    message.success('添加成功');
    return true;
  } catch (error) {
    hide();
    message.error('添加失败请重试！');
    return false;
  }
};

/**
 * 更新节点
 *
 * @param fields
 */
const handleUpdate = async (fields: TableListItem) => {
  const hide = message.loading('正在更新');

  try {
    await updateDict({ ...fields });
    hide();
    message.success('更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('更新失败请重试！');
    return false;
  }
};
/**
 * 删除节点
 *
 * @param selectedRows
 */

const handleRemove = async (selectedRows: TableListItem[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;

  try {
    await removeDict({
      key: selectedRows.map((row) => row.id),
    });
    hide();
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};

const TableList: React.FC = () => {
  /** 新建窗口的弹窗 */
  const [createDictVisible, handleDictVisible] = useState<boolean>(false);
  const [createDictItemVisible, handleDictItemVisible] = useState<boolean>(false);
  const [showDictDetail, setShowDictDetail] = useState<boolean>(false);
  const [showDictItemDetail, setShowDictItemDetail] = useState<boolean>(false);
  const dictFormRef = useRef<FormInstance>();
  const dictItemFormRef = useRef<FormInstance>();
  const actionRef = useRef<ActionType>();
  const itemActionRef = useRef<ActionType>();
  const [dictCurrentRow, setDictCurrentRow] = useState<TableListItem>();
  const [dictItemCurrentRow, setDictItemCurrentRow] = useState<TableListItem>();

  const onDictFill = (record: any) => {
    dictFormRef?.current?.setFieldsValue(record);
  };
  const onDictItemFill = (record: any) => {
    dictItemFormRef?.current?.setFieldsValue(record);
  };

  const columns: ProColumns<TableListItem>[] = [
    {
      title: '字典名称',
      dataIndex: 'name',
      render: (dom, entity) => {
        return (
          <a
            onClick={() => {
              setDictCurrentRow(entity);
              setShowDictDetail(true);
            }}
          >
            {dom}
          </a>
        );
      },
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
        <a key="subscribeAlert" >
          删除
        </a>,
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
      render: (_: any, record: any) => [
        <a key="update"
          onClick={() => {
            handleDictItemVisible(true);
            setDictItemCurrentRow(record);
            onDictItemFill(record);
          }}
        >
          编辑
        </a>,
        <a key="subscribeAlert" >
          删除
        </a>,
      ],
    },
  ]

  const formItemLayout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 19 },
  };

  return (
    <PageContainer>
      <ProTable<TableListItem, TableListPagination>
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
              handleDictVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={dict}
        columns={columns}
      />
      <ModalForm
        title="新建字典"
        width="500px"
        {...formItemLayout}
        formRef={dictFormRef}
        visible={createDictVisible}
        layout='horizontal'
        onVisibleChange={handleDictVisible}
        onFinish={async (value) => {
          const data = { ...dictCurrentRow, ...value };
          let success;
          if (!data.id) {
            success = await handleAdd(data as TableListItem);
          } else {
            success = await handleUpdate(data as TableListItem);
          }
          if (success) {
            handleDictVisible(false);
            dictFormRef?.current?.resetFields();
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          rules={[
            {
              required: true,
              message: '字典名称为必填项',
            },
          ]}
          label="字典名称"
          name="name"
        />
        <ProFormText
          rules={[
            {
              required: true,
              message: '字典编号为必填项',
            },
          ]}
          label="字典编号"
          name="code"
        />
        <ProFormTextArea label="描述" name="remark" />
      </ModalForm>

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
            columns={dictItemColumns}
            params={{
              dictId: dictCurrentRow?.id,
            }}
            request={dictItem}
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
            const data = { ...dictItemCurrentRow, ...value };
            let success;
            if (!data.id) {
              success = await handleAdd(data as TableListItem);
            } else {
              success = await handleUpdate(data as TableListItem);
            }
            if (success) {
              handleDictVisible(false);
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

      <Drawer
        width={600}
        visible={showDictDetail}
        onClose={() => {
          setDictCurrentRow(undefined);
          setShowDictItemDetail(false);
        }}
        closable={false}
      >
        {dictCurrentRow?.name && (
          <ProDescriptions<TableListItem>
            column={2}
            title={dictCurrentRow?.name}
            request={async () => ({
              data: dictCurrentRow || {},
            })}
            params={{
              id: dictCurrentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<TableListItem>[]}
          />
        )}
      </Drawer>
    </PageContainer >
  );
};

export default TableList;
