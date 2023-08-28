import React, {  } from 'react';
import { Switch } from 'antd';
import type { ProColumns } from '@ant-design/pro-table';

import type { ApiDomainField, ViewFormColumn } from './data';
import ConfigTab from './ConfigTab';

interface FormConfigTabProps {
  transferDataSource: ApiDomainField[];
  onValuesChange: (values: any) => void;
}

const columns: ProColumns<ViewFormColumn>[] = [
  {
    title: '字段名',
    dataIndex: 'field',
    editable: false,
    formItemProps: {
      rules: [
        {
          required: true,
          whitespace: false,
          message: '此项是必填项',
        }
      ],
    },
  },
  {
    title: '标签名',
    key: 'title',
    dataIndex: 'title',
    formItemProps: {
      rules: [
        {
          required: true,
          whitespace: false,
          message: '此项是必填项',
        }
      ],
    },
  },
  {
    title: '组件',
    key: 'component',
    dataIndex: 'component',
    valueType: 'select',
    valueEnum: {
      String: { text: 'String' },
      Integer: { text: 'Integer' },
      Datetime: { text: 'Datetime' },
    },
  },
  {
    title: '是否必填',
    dataIndex: 'required',
    renderFormItem: () => {
      return <Switch />;
    },
  },
  {
    title: '字段长度',
    dataIndex: 'length',
    valueType: 'digit',
    width: 100,
  },
  {
    title: '校验规则',
    key: 'ruleEnum',
    dataIndex: 'ruleEnum',
    valueType: 'select',
    valueEnum: {
      String: { text: 'String' },
      Integer: { text: 'Integer' },
      Datetime: { text: 'Datetime' },
    },
  },
  {
    title: '校验提示',
    dataIndex: 'ruleMsg',
  },
  {
    title: '排序',
    width: 50,
    dataIndex: 'sort',
    valueType: 'digit',
    editable: false
  },
  {
    title: '操作',
    valueType: 'option',
  }
];

const FormConfigTab: React.FC<FormConfigTabProps> = (props) => {
  return <>
    <ConfigTab<ViewFormColumn>
      transferDataSource={props.transferDataSource}
      columns={columns}
      mapApiDomainFieldToT={(item) => {
        return {
          id: item.id,
          field: item.name,
          title: item.comment,
          length: item.length,
          sort: item.sort,
          search: false
        } as ViewFormColumn;
      }}
      onValuesChange={props.onValuesChange} />
  </>
}

export default FormConfigTab;