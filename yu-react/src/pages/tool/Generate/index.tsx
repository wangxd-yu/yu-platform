import React, { useEffect, useRef, useState } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';
import 'prismjs/plugins/line-numbers/prism-line-numbers';
import 'prismjs/plugins/line-numbers/prism-line-numbers.css'

import 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import PrismCode from '@/components/Yu/PrismCode';
import type { TabsProps } from 'antd';
import { Drawer, Steps, Tabs, message } from 'antd';
import type { ApiDomainField, ViewFormColumn, ViewTableQuery, ViewTableColumn } from './data';
import DbConfigTab from './DbConfigTab';
import FormConfigTab from './FormConfigTab';
import { ProFormInstance, ProFormText, StepsForm } from '@ant-design/pro-form';
import TableConfigTab from './TableConfigTab';
import ModuleConfigTab from './ModuleConfigTab';
import CodeTree from './CodeTree';
import { request } from 'umi';

const GeneratePage: React.FC<any> = () => {
  //最终对象，根据这个对象生成code
  const [moduleDO, setModuleDO] = useState<any>({});

  const [moduleProps, setModuleProps] = useState<any>({});
  const [genApiDomainDO, setGenApiDomainDO] = useState<any>({});
  const [genViewFormDO, setGenViewFormDO] = useState<any>({});
  const [genViewTableDO, setGenViewTableDO] = useState<any>({});

  const [codeStrs, setCodeStrs] = useState<string[]>([]);

  const formRef = useRef<ProFormInstance>();

  // 使用 useEffect 监听 moduleProps 变化
  useEffect(() => {

    genApiDomainDO.domainName = moduleProps.moduleName

    // 在 moduleProps 变化时调用触发
    moduleDO.genApiModuleDO = {
      author: moduleProps?.author,
      packagePath: moduleProps?.packagePath,
      // 在 genApiDomainDO 变化时调用触发
      genApiDomainDO: genApiDomainDO
    }

    genViewTableDO.title = moduleProps.moduleTitle
    moduleDO.genViewModuleDO = {
      moduleName: moduleProps.moduleName,
      type: "CRUD",
      genViewCrudDO: {
        genViewFormDO: genViewFormDO,
        genViewTableDO: genViewTableDO
      }
    }

    // 更新 moduleDO
    setModuleDO(moduleDO);
  }, [moduleProps, genApiDomainDO, genViewFormDO, genViewTableDO]); // 当 moduleProps、genApiDomainDO 变化时触发 useEffect

  const onMainChange = (key: string) => {
    console.log(key);
    console.log("moduleDO", moduleDO)
    if (key === '2') {
      request('getTest1', {
        method: 'POST',
        data: moduleDO,
      }).then(res => console.log("res", setCodeStrs(res)));
    }
  }
  const onChange = (key: string) => {

    console.log(key);
  };

  const moduleConfigTabContent = (
    // 模块配置
    <ModuleConfigTab
      onValuesChange={setModuleProps}
    />
  );

  const dbConfigTabContent = (
    <DbConfigTab
      dataSource={genApiDomainDO}
      onValuesChange={setGenApiDomainDO}
    />
  );
  const formConfigTabContent = (
    <FormConfigTab
      transferDataSource={genApiDomainDO.domainFields}
      onValuesChange={setGenViewFormDO} />
  );

  const tableConfigTabContent = (
    <TableConfigTab
      transferDataSource={genApiDomainDO.domainFields}
      onValuesChange={setGenViewTableDO} />
  )

  const items: TabsProps['items'] = [
    {
      key: '1',
      label: `模块属性`,
      children: moduleConfigTabContent,
    },
    {
      key: '2',
      label: `数据库属性`,
      children: dbConfigTabContent,
    },
    {
      key: '3',
      label: `表单字段`,
      children: formConfigTabContent,
    },
    {
      key: '4',
      label: `表格字段`,
      children: tableConfigTabContent,
    },
  ];



  // 高亮所有代码
  /* useEffect(() => {
    Prism.highlightAll();
  }, []); */

  const steps = <StepsForm<{
    name: string;
  }>
    formRef={formRef}
    onFinish={async () => {
      message.success('提交成功');
    }}
    formProps={{
      validateMessages: {
        required: '此项为必填项',
      },
    }}
  >
    <StepsForm.StepForm<{
      name: string;
    }>
      name="base"
      title="模块属性"
      stepProps={{
        description: '这里填入的都是基本信息',
      }}
      onFinish={async () => {
        console.log(formRef.current?.getFieldsValue());
        return true;
      }}
    >
      {moduleConfigTabContent}
    </StepsForm.StepForm>
    <StepsForm.StepForm<{
      checkbox: string;
    }>
      name="checkbox"
      title="数据库属性"
      stepProps={{
        description: '这里填入运维参数',
      }}
      onFinish={async () => {
        console.log(formRef.current?.getFieldsValue());
        return true;
      }}
    >
      {dbConfigTabContent}
    </StepsForm.StepForm>
    <StepsForm.StepForm<{
      checkbox: string;
    }>
      name="111"
      title="表单字段"
      stepProps={{
        description: '这里填入运维参数',
      }}
      onFinish={async () => {
        console.log(formRef.current?.getFieldsValue());
        return true;
      }}
    >
      {genApiDomainDO.domainFields ? formConfigTabContent : '123'}
    </StepsForm.StepForm>
    <StepsForm.StepForm<{
      checkbox: string;
    }>
      name="222"
      title="表格字段"
      stepProps={{
        description: '这里填入运维参数',
      }}
      onFinish={async () => {
        console.log(formRef.current?.getFieldsValue());
        return true;
      }}
    >
      {genApiDomainDO.domainFields ? tableConfigTabContent : '333'}
    </StepsForm.StepForm>
  </StepsForm>

  const collectItems: TabsProps['items'] = [
    {
      key: '1',
      label: `配置`,
      /* children: (<Tabs defaultActiveKey="1" items={items} onChange={onChange} />), */
      children: steps,
    },
    {
      key: '2',
      label: `预览`,
      children: (<CodeTree moduleDO={moduleDO} codeStrs={codeStrs} />),
    }
  ]

  return (<>
    <PageContainer header={{ breadcrumb: {} }}>
      {/* 格式化代码 */}
      {/* <pre className="line-numbers">
        <code className="language-javascript">{`
          function add(a, b) {
            return a + b;
          }
        `}
        </code>
      </pre>
 */}
      <PrismCode
        code={`
    function add(a, b) {
      return a + b1111;
    }
  `}
        language="js"
        plugins={["line-numbers"]}
      />
    </PageContainer >
    <Drawer
      title="Basic Drawer"
      placement="bottom"
      closable={true}
      height="100vh"
      onClose={() => { }}
      open={true}>
      <Tabs defaultActiveKey="1" tabPosition="left" items={collectItems} onChange={onMainChange} />
    </Drawer>

  </>
  );
};
export default GeneratePage;
