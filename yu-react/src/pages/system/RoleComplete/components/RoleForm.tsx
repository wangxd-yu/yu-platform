import React from 'react'
import { ProFormText, ProFormTextArea } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';


const RoleForm: React.FC<YuFormProps> = (props: YuFormProps) => {
  return (
    <YuForm {...props}>
      <ProFormText
        rules={[
          {
            required: true,
            message: '角色名称为必填项',
          },
        ]}
        label="角色名称"
        name="name"
      />
      <ProFormText
        rules={[
          {
            required: true,
            message: '角色编号为必填项',
          },
        ]}
        label="角色编号"
        name="code"
      />
      <ProFormTextArea label="描述" name="remark" />
    </YuForm >
  )
}
export default RoleForm;