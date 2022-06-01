import React, { ReactChild, ReactFragment, ReactPortal } from 'react'
import ProForm, { ModalForm, DrawerForm } from '@ant-design/pro-form'
import type { ModalFormProps } from '@ant-design/pro-form';
import YuDraggleModal from './YuDraggleModel';

export declare type FormType = 'ProForm' | 'YuDraggleModal' | 'ModalForm' | 'DrawerForm';

export type YuFormProps = ModalFormProps & {
    title?: ((boolean | ReactChild | ReactFragment | ReactPortal | null) & string) | undefined;
    formType?: FormType;
    isAdd?: boolean;
    children?: React.ReactNode;
    formRef?: any;
}

// declare function ModalForm<T = Record<string, any>>({ children, trigger, onVisibleChange, modalProps, onFinish, title, width, ...rest }: ModalFormProps<T>): JSX.Element;
const YuForm: React.FC<YuFormProps> = ((props: YuFormProps) => {
    console.log('props.formType', props.formType)
    const Components = {
        ProForm,
        YuDraggleModal,
        ModalForm,
        DrawerForm,
    };
    const formItemLayout = props.layout === 'horizontal' ? {
        labelCol: { span: 5 },
        wrapperCol: { span: 19 },
    } : {};
    const FormComponents = props?.formType ? Components[props?.formType] : Components.YuDraggleModal;

    const { formType, isAdd, children, title, ...proFormProps } = props

    return (
        <FormComponents
            title={title}
            layout='horizontal'
            {...formItemLayout}
            {...proFormProps}
        >
            {children}
        </FormComponents >
    )
})

export default YuForm;