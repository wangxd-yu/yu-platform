import React from 'react'
import { ProFormText, ProFormTextArea } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';


const DictForm1: React.FC<YuFormProps> = (props: YuFormProps) => {
  return (
    <YuForm {...props}>
      <ProFormText
        rules={[
          {
            required: true,
            message: '字典名称为必填项',
          },
        ]}
        label="字典名称1"
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
    </YuForm >
  )
}
export default DictForm1;