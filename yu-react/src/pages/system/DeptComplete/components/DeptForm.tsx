import React, { Fragment, useRef } from 'react'
import type { FormInstance } from '@ant-design/pro-form';
import ProForm, { ProFormDigit, ProFormRadio, ProFormText } from '@ant-design/pro-form'
import { getDeptTypesByDeptId, updateDept } from '../service'
import { getDept } from '../../Dept/service';
import type { DeptData } from '../data';
import { Col, Row, Space } from 'antd';
import * as YuCrud from '@/utils/yuCrud';
import { useState } from 'react';

const formItemLayout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 12 },
};

const DeptForm: React.FC<{ id: string, dept: DeptData, deptUpdate: () => any }> = (prop: { id: string, dept: DeptData, deptUpdate: () => any }) => {
    const [rawDept, setRawDept] = useState<DeptData>();
    const formRef = useRef<FormInstance>();

    return (
        <ProForm<DeptData>
            formRef={formRef}
            layout={'horizontal'}
            {...formItemLayout}
            params={{ id: prop.id }}
            initialValues={{ rawDept }}
            request={(params) => getDept(params.id)}
            onInit={(value) => { setRawDept(value) }}
            onFinish={
                (value) => {
                    const data = { ...rawDept, ...value }
                    return YuCrud.handleUpdate(data, (_data) => {
                        updateDept(_data)
                        return prop.deptUpdate()
                    })
                }
            }
            submitter={{
                render: (props, doms) => {
                    return (
                        <Row style={{ textAlign: 'center' }}>
                            <Col span={12} offset={6}>
                                <Space>{doms}</Space>
                            </Col>
                        </Row>
                    );
                },
            }}
        >
            {prop.dept.id !== '0' ?
                <Fragment>
                    <ProFormText
                        rules={[
                            {
                                required: true,
                                message: '部门名称为必填项',
                            },
                        ]}
                        label="部门名称"
                        name="name"
                    />
                    {/* <ProFormText disabled label="上级部门">{props?.pDept.name}</ProFormText> */}

                    <ProFormRadio.Group
                        name="typeId"
                        label="部门类型"
                        params={{ deptId: prop?.dept?.id }}
                        request={getDeptTypesByDeptId}
                    />
                    <ProFormDigit rules={[
                        {
                            required: true,
                            message: '排序为必填项',
                        },
                    ]} label="排序" name="sort" min={1} max={99} />
                </Fragment>
                :
                <Fragment>
                    <ProFormText
                        rules={[
                            {
                                required: true,
                                message: '部门名称为必填项',
                            },
                        ]}
                        label="单位名称"
                        name="name"
                    />
                </Fragment>
            }
        </ProForm>
    )
}

export default DeptForm;