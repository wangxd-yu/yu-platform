import React, { useState, useEffect } from 'react'
import ProForm, { ProFormDependency, ProFormSelect, ProFormText } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';
import { yuUrlSystem } from '@/utils/yuUrl';
import { TreeSelect } from 'antd';
import * as YuApi from '@/utils/yuApi';
import type { DataNode } from 'rc-tree/lib/interface';
import _ from 'lodash'

type UserFromProps = YuFormProps & { deptTree: DataNode[] | undefined }
type EndpointLessDTO = {
  pattern: string, 
  method: string
}

const UserForm: React.FC<YuFormProps & UserFromProps> = (props: UserFromProps) => {
  const [endpointList, setEndpointList] = useState<Map<string,EndpointLessDTO[]>>(new Map());

  useEffect(() => {
    YuApi.get<EndpointLessDTO>(yuUrlSystem('/endpoint/all')).then(res => {
      setEndpointList(new Map(Object.entries<EndpointLessDTO[]>(_.groupBy(res, 'pattern') as unknown as {pattern: EndpointLessDTO[]})))
    });
  }, []);

  return (
    <YuForm {...props}>
      <ProFormSelect
          name="pattern"
          label="请求路径"
          showSearch
          options={Array.from(endpointList?.keys()).map(item => {
            return {
              value: item,
              label: item
            }
          })}
          placeholder="请选择"
          rules={[{ required: true, message: '请选择请求路径' }]}
          fieldProps={{onChange: () => {
            //console.log(endpointList?.get(props?.formRef?.current.getFieldValue('pattern')))
            //console.log(props.formRef.current.getFieldValue('pattern'))
          }}}
        />
      <ProFormDependency name={['pattern']}>
        {({ pattern }) => {
           return (
            <ProFormSelect
                name="method"
                label="请求方法"
                options={endpointList?.get(pattern)?.map(item => {
                  return {
                    value: item.method,
                    label: item.method
                  }
                })}
                placeholder="请选择"
                rules={[{ required: true, message: '请选择请求路径' }]}
              />
        );
      }}
      </ProFormDependency>
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
          const res = await YuApi.queryList<{ name: string; id: string; }>(yuUrlSystem("/role"));
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