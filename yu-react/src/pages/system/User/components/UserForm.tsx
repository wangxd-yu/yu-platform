import React from 'react'
import ProForm, { ProFormSelect, ProFormSwitch, ProFormText } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';
import { TreeSelect } from 'antd';
import * as YuApi from '@/utils/yuApi';
import type { DataNode } from 'rc-tree/lib/interface';

type UserFromProps = YuFormProps & { deptTree: DataNode[] | undefined }

const UserForm: React.FC<YuFormProps & UserFromProps> = (props: UserFromProps) => {
  return (
    <YuForm {...props}>
      <ProFormSwitch name="enabled" label="状态" />
      <ProFormText
        rules={[
          {
            required: true,
            message: '账号称为必填项',
          },
        ]}
        label="账号"
        name="username"
      />
      <ProFormText
        rules={[
          {
            required: true,
            message: '昵称为必填项',
          },
        ]}
        label="昵称"
        name="name"
      />
      <ProForm.Item label="部门" name="deptNo" rules={[
        {
          required: true,
          message: '部门为必选项',
        },
      ]}>
        <TreeSelect
          style={{ width: '100%' }}
          listHeight={300}
          dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
          treeData={props.deptTree}
          placeholder="请选择"
          treeDefaultExpandAll
        />
      </ProForm.Item>
      <ProFormSelect
        fieldProps={{
          mode: 'multiple',
        }}
        name="roleIds"
        label="角色"
        request={async () => {
          const res = await YuApi.queryListReq<{ name: string; id: string; }>("/api_sy/role");
          return res.data.map((item) => {
            return {
              label: item.name,
              value: item.id
            };
          });
        }}
        rules={[{ required: true, message: '角色为必选项!' }]}
      />
    </YuForm >
  )
}
export default UserForm;