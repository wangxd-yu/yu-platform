import React from 'react'
import { ProFormDigit, ProFormRadio, ProFormText } from '@ant-design/pro-form'
import type { DeptData } from '../data';
import type { YuFormProps } from '@/components/Yu/YuForm';
import { addDept, updateDept } from '../service'
import { getSubDeptTypes } from '../service'
import * as YuCrud from '@/utils/yuCrud';
import YuForm from '@/components/Yu/YuForm';

export type DeptFormProps = YuFormProps & {
    deptDataList: DeptData[];
}

const formItemLayout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 19 },
};

const DeptForm: React.FC<YuFormProps & {pDept: DeptData}> = (props: YuFormProps & {pDept: DeptData}) => {
    console.log(props.initialValues)
    return (
        <YuForm
            title={!props.isAdd ? '更新部门' : '新建部门'}
            width="600px"
            formRef={props.formRef}
            {...formItemLayout}
            visible={props.visible}
            layout='horizontal'
            onVisibleChange={(visible) => {
                if (props.onVisibleChange) props.onVisibleChange(visible);
            }}
            onFinish={
                async (value) => {
                    const data = { ...props.initialValues, ...value };
                    let success;
                    if (!data.id) {
                        success = await YuCrud.handleAdd(data as DeptData, addDept);
                    } else {
                        success = await YuCrud.handleUpdate(data as DeptData, updateDept);
                    }
                    if (success && props.onFinish) {
                        props.onFinish(value);
                    }
                }
            }
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
            {props.initialValues?.pid &&
                <ProFormText disabled label="上级部门">{props?.pDept.name}</ProFormText>
            }
            <ProFormRadio.Group
                rules = {[
                    {
                        required: true,
                        message: '部门类型为必填项',
                    },
                ]}
                name="typeId"
                label="部门类型"
                params={{ typeId: props.pDept?.typeId }}
                request={getSubDeptTypes}
            />
            <ProFormDigit rules={[
                {
                    required: true,
                    message: '排序为必填项',
                },
            ]} label="排序" name="sort" min={1} max={99} />
        </YuForm>
    )
}

export default DeptForm;