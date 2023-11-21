import React, {  ReactFragment, ReactPortal } from 'react'
import YuDraggleModal from './YuDraggleModel';
import { DrawerForm, ModalForm, ModalFormProps, ProForm } from '@ant-design/pro-components';

export declare type FormType = 'ProForm' | 'YuDraggleModal' | 'ModalForm' | 'DrawerForm';

export type YuFormProps = ModalFormProps & {
    title?: ((boolean | ReactFragment | ReactPortal | null) & string) | undefined;
    formType?: FormType;
    isAdd?: boolean;
    children?: React.ReactNode;
    formRef?: any;
}

// declare function ModalForm<T = Record<string, any>>({ children, trigger, onVisibleChange, modalProps, onFinish, title, width, ...rest }: ModalFormProps<T>): JSX.Element;
const YuForm: React.FC<YuFormProps> = ((props: YuFormProps) => {
    const Components = {
        ProForm,
        YuDraggleModal,
        ModalForm,
        DrawerForm,
    };
    let laylout = props?.layout
    laylout = laylout ? laylout : 'horizontal'
    const formItemLayout = laylout === 'horizontal' ? {
        labelCol: { span: 5 },
        wrapperCol: { span: 19 },
    } : {};
    const FormComponents = props?.formType ? Components[props?.formType] : Components.YuDraggleModal;

    const { formType, isAdd, children, title, ...proFormProps } = props

    return (
        <FormComponents
            title={title}
            layout={laylout}
            {...formItemLayout}
            {...proFormProps}
        >
            {children}
        </FormComponents >
    )
})

export default YuForm;