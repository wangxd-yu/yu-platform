import React, { Fragment, useRef, useState } from 'react'
import type { FormInstance } from '@ant-design/pro-form';
import ProForm, { ProFormDigit, ProFormRadio, ProFormText } from '@ant-design/pro-form'
import { getDeptTypesByDeptId, updateDept } from '../service'
import { getDept } from '../service';
import type { DeptData } from '../data';
import { Col, Row, Space, Spin } from 'antd';
import * as YuCrud from '@/utils/yuCrud';
import { YuFormProps } from '@/components/Yu/YuForm';

const formItemLayout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 12 },
};

type DeptFromProps = YuFormProps & { id: string, dept: DeptData, deptUpdate: () => any }

const DeptForm: React.FC<YuFormProps & DeptFromProps> = (props: DeptFromProps) => {
    const [rawDept, setRawDept] = useState<DeptData>();
    const [loading, setLoading] = useState<boolean>(true);
    const formRef = useRef<FormInstance>();


    return (
        <Spin tip="Loading..." spinning={loading}>
            <ProForm<DeptData>
                formRef={formRef}
                layout={'horizontal'}
                {...formItemLayout}
                params={{ id: props.id }}
                /* initialValues={{ rawDept }} */
                request={async (params) => {
                    setLoading(true)
                    const value = await getDept(params.id)
                    setRawDept(value);
                    //formRef.current?.setFieldsValue(value)
                    setLoading(false)
                    return Promise.resolve(value)
                }}
                /*  onInit={(value) => { setRawDept(value) }} */
                onFinish={
                    (value) => {
                        const data = { ...rawDept, ...value }
                        return YuCrud.handleUpdate(data, async (_data) => {
                            await updateDept(_data)
                            return props.deptUpdate()
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
                {props.dept.id !== '0' ?
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
                            params={{ deptId: props?.dept?.id }}
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
                                    message: '单位名称为必填项',
                                },
                            ]}
                            label="单位名称"
                            name="name"
                        />
                    </Fragment>
                }
            </ProForm>
        </Spin>
    )
}

export default DeptForm;
