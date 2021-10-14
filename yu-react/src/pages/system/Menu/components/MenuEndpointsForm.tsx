import React, { useState, useEffect } from 'react'
import { ProFormDependency, ProFormSelect, ProFormSwitch, ProFormText } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';
import { yuUrlSystem } from '@/utils/yuUrl';
import * as YuApi from '@/utils/yuApi';
import _ from 'lodash'

type EndpointLessDTO = {
  pattern: string, 
  method: string
}

const MenuEndpointsForm: React.FC<YuFormProps> = (props: YuFormProps) => {
  const [endpointList, setEndpointList] = useState<Map<string,EndpointLessDTO[]>>(new Map());

  useEffect(() => {
    YuApi.get<EndpointLessDTO>(yuUrlSystem('/endpoint/all')).then(res => {
      setEndpointList(new Map(Object.entries<EndpointLessDTO[]>(_.groupBy(res, 'pattern') as unknown as {pattern: EndpointLessDTO[]})))
    });
  }, []);

  return (
    <YuForm {...props}>
      <ProFormSwitch name="enabled" label="状态"/>
      <ProFormText
        rules={[
          {
            required: true,
            message: '端点名称为必填项',
          },
        ]}
        label="端点名称"
        name="label"
      />
      <ProFormSelect
          name="pattern"
          label="端点路径"
          showSearch
          options={Array.from(endpointList?.keys()).map(item => {
            return {
              value: item,
              label: item
            }
          })}
          placeholder="请选择"
          rules={[{ required: true, message: '请选择端点路径' }]}
          fieldProps={{onChange: () => {
            props.formRef?.current?.setFieldsValue({
              method: ''
            })
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
                fieldProps={{
                  defaultValue: ""
                }}
                placeholder="请选择"
                rules={[{ required: true, message: '请选择请求方法' }]}
              />
        );
      }}
      </ProFormDependency>
    </YuForm >
  )
}
export default MenuEndpointsForm;