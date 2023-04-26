import React, {Fragment, useEffect, useRef, useState} from 'react';
import type {FormInstance} from 'antd';
import {Button, Modal, Popconfirm, Transfer} from 'antd';
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type {DeptRoleData} from '../data';
import * as YuCrud from '@/utils/yuCrud';
import {addDeptRoles, batchDeleteDeptRoles, queryDeptRoles} from '../service'
import {PlusOutlined} from '@ant-design/icons';
import * as YuApi from '@/utils/yuApi'
import type {RoleData} from '../../Role/data';

type RoleTransferData = RoleData & { disabled: boolean }

const DeptRolePage: React.FC<{ deptId: string }> = (prop: { deptId: string }) => {
  const [deptRoleFormVisible, setDeptRoleFormVisible] = useState<boolean>(false);
  const [deptRoleList, setDeptRoleList] = useState<DeptRoleData[]>();
  const [roleList, setRoleList] = useState<RoleTransferData[]>([]);
  const [roleTargetKeys, setRoleTargetKeys] = useState<string[]>();
  const deptRoleFormRef = useRef<FormInstance>();
  const deptRoleActionRef = useRef<ActionType>();

    useEffect(() => {
        YuApi.queryList<RoleTransferData>('/role').then(res => {
            return setRoleList(res?.data);
        });
    }, []);

    const columns: ProColumns<DeptRoleData>[] = [
        {
            title: '角色名称',
            dataIndex: 'roleName'
        },
        {
            title: '角色描述',
            dataIndex: 'roleRemark'
        },
        {
            title: '绑定时间',
            align: 'center',
            dataIndex: 'bindTime'
        },
        {
            title: '操作',
            key: 'option',
            align: 'center',
            valueType: 'option',
            render: (_: any, record: any) => [
                <Popconfirm key="deleteConfirm" title={`确认删除该记录吗?`} okText="是" cancelText="否"
                    onConfirm={async () => {
                        console.log(record)
                        await YuCrud.handleDelete(batchDeleteDeptRoles, { deptId: prop.deptId, roleIds: [record.roleId] });
                        if (deptRoleActionRef.current) {
                            deptRoleActionRef.current.reload();
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

    const handleOk = async () => {
        const success = await YuCrud.handleAdd({ deptId: prop.deptId, roleIds: roleTargetKeys as string[] }, addDeptRoles);
        if (success) {
            deptRoleActionRef?.current?.reload();
            setDeptRoleFormVisible(false);
            setRoleTargetKeys([])
        }
    };

    const handleCancel = () => {
        setDeptRoleFormVisible(false);
    };

  /**
   * 角色绑定 穿梭框 选项在两栏之间转移时的回调函数
   * @param nextTargetKeys 结果
   */
    const handleChange = (nextTargetKeys: string[]) => {
        setRoleTargetKeys(nextTargetKeys);
    };

    return (
        <Fragment>
            <ProTable<DeptRoleData>
                columns={columns}
                actionRef={deptRoleActionRef}
                params={{ id: prop.deptId }}
                request={(params) => queryDeptRoles(params.id)}
                postData={(data) => {
                    setDeptRoleList(data as DeptRoleData[])
                    return data
                }}
                rowKey="roleId"
                search={false}
                dateFormatter="string"
                headerTitle="角色引用"
                toolBarRender={() => [
                    <Button
                        type="primary"
                        key="primary"
                        onClick={() => {
                            deptRoleFormRef?.current?.resetFields();
                          setDeptRoleFormVisible(true);
                          setRoleTargetKeys([])
                          roleList.map((item) => {
                            if (deptRoleList && (deptRoleList?.findIndex(deptRole => deptRole.roleId === item.id) > -1)) {
                              item.disabled = true
                            }
                          })
                        }}
                    >
                      <PlusOutlined/> 绑定
                    </Button>,
                ]}
            />
          {
            deptRoleFormVisible &&
            <Modal title="绑定角色" visible={deptRoleFormVisible} onOk={handleOk} onCancel={handleCancel}
                   okButtonProps={{
                     disabled: roleTargetKeys?.length == 0,
                   }}>
              <Transfer<RoleData>
                /* status="error" */
                dataSource={roleList}
                rowKey={item => item.id}
                titles={['可用', '已选']}
                render={item => item.name}
                targetKeys={roleTargetKeys}
                onChange={handleChange}
                listStyle={{
                  width: 300,
                  height: 300,
                }}
              />
            </Modal>
          }

        </Fragment>
    );
};

export default DeptRolePage;