import React, { useState, useEffect, useRef } from 'react';
import { PlusOutlined, UserOutlined } from '@ant-design/icons';
import { Avatar, Button, Col, Popconfirm, Popover, Row, Space, Switch } from 'antd';
import type { FormInstance } from 'antd';
import { PageContainer } from '@ant-design/pro-layout';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import { queryUser, getUser, deleteUser, disableUser, enableUser } from './service';
import * as YuCrud from '@/utils/yuCrud';
import type { UserData } from './data';
import UserForm from './components/UserForm'
import DeptTree from '@/components/Yu/DeptTree/index';
import ProCard from '@ant-design/pro-card';
import { DataNode } from 'antd/lib/tree';

/**
 * 获取第一个表格的可视化高度
 * @param {*} extraHeight 额外的高度(表格底部的内容高度 Number类型,默认为74)
 * @param {*} id 当前页面中有多个table时需要制定table的id
 */
const getTableScroll = ({ extraHeight, id }: any) => {
  if (!extraHeight) {
    //  默认底部分页64 + 边距10
    extraHeight = 74
  }
  let tHeader = null
  if (id) {
    tHeader = document.getElementById(id) ? document.getElementById(id)?.getElementsByClassName("ant-table-thead")[0] : null
  } else {
    tHeader = document.getElementsByClassName("ant-table-thead")[0]
  }
  //表格内容距离顶部的距离
  let tHeaderBottom = 0
  if (tHeader) {
    tHeaderBottom = tHeader.getBoundingClientRect().bottom
  }
  //窗体高度-表格内容顶部的高度-表格内容底部的高度
  // let height = document.body.clientHeight - tHeaderBottom - extraHeight
  let height = `calc(100vh - ${tHeaderBottom + extraHeight + window.scrollY + 30}px)`

  return height
}

const UserTable: React.FC<UserData> = () => {
  /** 新建窗口的弹窗 */
  const [userFormVisible, setUserFormVisible] = useState<boolean>(false);
  const [userCurrentRow, setUserCurrentRow] = useState<UserData>();
  const [isCurrent, setIsCurrent] = useState<boolean>(false);
  const [deptId, setDeptId] = useState<string>();
  const [deptTree, setDeptTree] = useState<DataNode[]>();
  const userFormRef = useRef<FormInstance>();
  const userActionRef = useRef<ActionType>();
  const [scrollY, setScrollY] = useState("")
  //页面加载完成后才能获取到对应的元素及其位置
  useEffect(() => {
    setScrollY(getTableScroll({ extraHeight: null, id: null }))
  }, [])

  const columns: ProColumns<UserData>[] = [
    {
      dataIndex: 'avatar',
      title: '头像',
      align: 'center',
      valueType: 'avatar',
      width: 150,
      search: false,
      render: (dom, record) => (
        <Popover content={
          <Space>
            {record.portraitUrl ?
              <Avatar size={{ xs: 24, sm: 32, md: 40, lg: 64, xl: 80, xxl: 100 }} src={`/${BASE_URL_PREFIX}/${record.portraitUrl}`} /> :
              <Avatar size={{ xs: 24, sm: 32, md: 40, lg: 64, xl: 80, xxl: 100 }} icon={<UserOutlined />} />
            }
          </Space>
        } trigger="hover">
          <Space>
            {record.portraitUrl ?
              <Avatar src={`/${BASE_URL_PREFIX}/${record.portraitUrl}`} /> :
              <Avatar icon={<UserOutlined />} />
            }
          </Space>
        </Popover>
      ),
    },
    {
      title: '用户账号',
      dataIndex: 'username'
    },
    {
      title: '用户昵称',
      dataIndex: 'name',
    },
    {
      title: '所属部门',
      dataIndex: 'deptFullName',
      search: false,
    },
    {
      title: '状态',
      align: 'center',
      dataIndex: 'enabled',
      valueType: 'select',
      valueEnum: {
        true: {
          text: '启用',
        },
        false: {
          text: '停用',
        }
      },
      render: (_: any, record: any) => [
        <Switch key="enabled" checked={record.enabled} checkedChildren="启用" unCheckedChildren="停用" onChange={() => {
          if (record.enabled) {
            disableUser(record.id)
          } else {
            enableUser(record.id)
          }
          userActionRef.current?.reload()
        }} />
      ],

    },
    {
      title: '创建时间',
      align: 'center',
      sorter: true,
      search: false,
      dataIndex: 'createTime',
      valueType: 'dateTime'
    },
    {
      title: '操作',
      align: 'center',
      dataIndex: 'option',
      valueType: 'option',
      render: (_: any, record: any) => [
        <a
          key="update"
          onClick={async () => {
            await getUser(record.id, setUserCurrentRow)
            setUserFormVisible(true);
          }}
        >
          编辑
        </a>,
        <Popconfirm key="deleteConfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
          onConfirm={async () => {
            await YuCrud.handleDelete(deleteUser, record.id)
            if (userActionRef.current) {
              userActionRef.current.reload();
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
    <PageContainer header={{
      breadcrumb: {},
    }}>

      <ProCard split="vertical" ghost >
        <ProCard style={{ height: 'calc(100vh - 168px)', background: "white" }} colSpan={{ xs: 12, sm: 12, md: 10, lg: 8, xl: 5 }}>
          <DeptTree
            onInit={(deptTree) => { setDeptTree(deptTree) }}
            onSelect={(deptId) => {
              setDeptId(deptId);
              userActionRef.current?.reload();
            }} />
        </ProCard>
        <ProCard ghost>
          <ProTable<UserData>
            toolbar={{
              title: (
                <Switch
                  checkedChildren="只看本级"
                  unCheckedChildren="本级及下级"
                  defaultChecked={isCurrent}
                  onChange={() => setIsCurrent(!isCurrent)} />
              )
            }}
            actionRef={userActionRef}
            rowKey="id"
            search={{
              labelWidth: 120,
              onCollapse: () => {
                setScrollY(getTableScroll({ extraHeight: null, id: null }))
              }
            }}
            scroll={{ y: scrollY, x: '800px'}}

            toolBarRender={() => [
              <Button
                type="primary"
                key="primary"
                onClick={() => {
                  userFormRef?.current?.resetFields();
                  setUserCurrentRow(undefined)
                  setUserFormVisible(true);
                }}
              >
                <PlusOutlined /> 新建
              </Button>,
            ]}
            params={{ deptId, isCurrent }}
            request={queryUser}
            columns={columns}
            pagination={{
              showSizeChanger: true,
            }}
          />
        </ProCard>
      </ProCard>

      {userFormVisible && <UserForm
        deptTree={deptTree}
        width="700px"
        title={userCurrentRow?.id ? '更新用户' : '新建用户'}
        visible={userFormVisible}
        onVisibleChange={(visible) => {
          if (visible) {
            userFormRef?.current?.resetFields();
          } else {
            setUserFormVisible(false);
            setUserCurrentRow(undefined);
          }
        }}
        formRef={userFormRef}
        initialValues={userCurrentRow || { enabled: true }}
        onFinish={async () => {
          setUserFormVisible(false);
          userFormRef?.current?.resetFields();
          if (userActionRef.current) {
            userActionRef.current.reload();
          }
        }}
      />
      }
    </PageContainer >
  );
};
export default UserTable;
