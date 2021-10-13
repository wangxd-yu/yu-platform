import React from 'react'
import { ModalForm, DrawerForm } from '@ant-design/pro-form'
import type { ModalFormProps } from '@ant-design/pro-form';

export type YuFormProps = ModalFormProps & {
    formType?: string;
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
    const Components = {
        ModalForm,
        DrawerForm
    };
    const FormComponents = props?.formType ? Components[props?.formType] : Components.ModalForm;
    
    return (
        <FormComponents
            layout='horizontal'
            {...formItemLayout}
            {...props}
        >
            {props.children}
        </FormComponents >
    )
})

export default YuForm;