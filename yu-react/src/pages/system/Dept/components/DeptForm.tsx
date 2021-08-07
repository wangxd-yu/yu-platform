import React from 'react'
import { ModalForm, ProFormDigit, ProFormRadio, ProFormText } from '@ant-design/pro-form'
import type { DeptData } from '../data';
import type { YuFormProps } from '@/components/Yu/YuForm';
import { getSubDeptTypes } from '../service'

export type DeptFormProps = YuFormProps & {
    deptDataList: DeptData[];
}

const formItemLayout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 19 },
};

const DeptForm: React.FC<DeptFormProps> = (props: DeptFormProps) => {

    return (
        <ModalForm
            title={!props.isAdd ? '更新部门' : '新建部门'}
            width="600px"
            formRef={props.formRef}
            {...formItemLayout}
            visible={props.visible}
            layout='horizontal'
            onVisibleChange={(visible) => {
                if (props.onVisibleChange) props.onVisibleChange(visible);
            }}
            onFinish={props.onFinish}
            initialValues={props.initialValues}
        >
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
            {props.initialValues?.pno &&
                <ProFormText name="pName" disabled label="上级部门" />
            }
            <ProFormRadio.Group
                name="typeId"
                label="部门类型"
                params={{typeId: props.initialValues?.typeId}}
                request={getSubDeptTypes}
            />
            <ProFormDigit rules={[
                {
                    required: true,
                    message: '排序为必填项',
                },
            ]} label="排序" name="sort" min={1} max={99} />
        </ModalForm >
    )
}

export default DeptForm;