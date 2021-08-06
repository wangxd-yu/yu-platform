import React from 'react'
import { ModalForm, ProFormText, ProFormTextArea } from '@ant-design/pro-form'

export type DictFormProps = {
    onFinish?: (values: any) => Promise<void>;
    onVisibleChange?: (visible: boolean) => void;
    isAdd?: boolean;
    visible: boolean;
}

const formItemLayout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 19 },
};

const DictForm = React.forwardRef((props: DictFormProps, ref: any) => {
    return (
        <ModalForm
            title={!props.isAdd ? '更新字典' : '新建字典'}
            width="500px"
            {...formItemLayout}
            formRef={ref}
            visible={props.visible}
            layout='horizontal'
            onVisibleChange={props.onVisibleChange}
            onFinish={props.onFinish}
      >
        <ProFormText
          rules={[
            {
              required: true,
              message: '字典名称为必填项',
            },
          ]}
          label="字典名称"
          name="name"
        />
        <ProFormText
          rules={[
            {
              required: true,
              message: '字典编号为必填项',
            },
          ]}
          label="字典编号"
          name="code"
        />
        <ProFormTextArea label="描述" name="remark" />
      </ModalForm >
    )
})

export default DictForm;