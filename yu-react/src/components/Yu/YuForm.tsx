import React from 'react'
import { ModalForm } from '@ant-design/pro-form'
import type { ModalFormProps } from '@ant-design/pro-form';

export type YuFormProps = ModalFormProps & {
    isAdd?: boolean;
    children?: React.ReactNode;
    formRef?: any;
}

const formItemLayout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 19 },
};
// declare function ModalForm<T = Record<string, any>>({ children, trigger, onVisibleChange, modalProps, onFinish, title, width, ...rest }: ModalFormProps<T>): JSX.Element;
const YuForm: React.FC<YuFormProps> = ((props: YuFormProps) => {
    return (
        <ModalForm
            title={!props.isAdd ? '更新字典' : '新建字典'}
            width="500px"
            {...formItemLayout}
            formRef={props.formRef}
            visible={props.visible}
            layout='horizontal'
            onVisibleChange={props.onVisibleChange}
            onFinish={props.onFinish}
      >
        {props.children}
      </ModalForm >
    )
})

export default YuForm;