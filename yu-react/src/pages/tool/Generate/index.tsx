import React, { useEffect, useRef, useState } from 'react';

import PrismCode from '@/components/Yu/PrismCode';
import type { ProFormInstance } from '@ant-design/pro-components';
import { PageContainer, StepsForm } from '@ant-design/pro-components';
import { request } from '@umijs/max';
import type { TabsProps } from 'antd';
import { Drawer, Tabs, message } from 'antd';
import CodeTree from './CodeTree';
import DbConfigTab from './DbConfigTab';
import FormConfigTab from './FormConfigTab';
import ModuleConfigTab from './ModuleConfigTab';
import TableConfigTab from './TableConfigTab';

const GeneratePage: React.FC<any> = () => {
  //最终对象，根据这个对象生成code
  const [moduleDO, setModuleDO] = useState<any>({});

  const [moduleProps, setModuleProps] = useState<any>({});
  const [genApiDomainDO, setGenApiDomainDO] = useState<any>({});
  const [genViewFormDO, setGenViewFormDO] = useState<any>({});
  const [genViewTableDO, setGenViewTableDO] = useState<any>({});

  const [codeStrs, setCodeStrs] = useState<string[]>([]);

  const formRef = useRef<ProFormInstance>();
  // dbRef
  const dbConfigTabRef = useRef<{ validate: () => any } | null>(null);
  const formConfigTabRef = useRef<{ validate: () => any } | null>(null);
  const tableConfigTabRef = useRef<{ validate: () => any } | null>(null);

  // 使用 useEffect 监听 moduleProps 变化
  useEffect(() => {
    genApiDomainDO.domainName = moduleProps.moduleName;

    // 在 moduleProps 变化时调用触发
    moduleDO.genApiModuleDO = {
      author: moduleProps?.author,
      packagePath: moduleProps?.packagePath,
      // 在 genApiDomainDO 变化时调用触发
      genApiDomainDO: genApiDomainDO,
    };

    genViewTableDO.title = moduleProps.moduleTitle;
    moduleDO.genViewModuleDO = {
      moduleName: moduleProps.moduleName,
      type: 'CRUD',
      genViewCrudDO: {
        genViewFormDO: genViewFormDO,
        genViewTableDO: genViewTableDO,
      },
    };

    // 更新 moduleDO
    setModuleDO(moduleDO);
  }, [moduleProps, genApiDomainDO, genViewFormDO, genViewTableDO]); // 当 moduleProps、genApiDomainDO 变化时触发 useEffect

  const onMainChange = (key: string) => {
    console.log(key);
    console.log('moduleDO', moduleDO);
    if (key === '2') {
      request('getTest1', {
        method: 'POST',
        data: moduleDO,
      }).then((res) => console.log('res', setCodeStrs(res)));
    }
  };

  const moduleConfigTabContent = (
    // 模块配置
    <ModuleConfigTab onValuesChange={setModuleProps} />
  );

  const dbConfigTabContent = <DbConfigTab ref={dbConfigTabRef} /* dataSource={genApiDomainDO} onValuesChange={setGenApiDomainDO} */ />;
  const formConfigTabContent = (
    <FormConfigTab ref={formConfigTabRef} transferDataSource={genApiDomainDO.domainFields} onValuesChange={setGenViewFormDO} />
  );

  const tableConfigTabContent = (
    <TableConfigTab ref={tableConfigTabRef} transferDataSource={genApiDomainDO.domainFields} onValuesChange={setGenViewTableDO} />
  );

  // 高亮所有代码
  /* useEffect(() => {
    Prism.highlightAll();
  }, []); */

  const steps = (
    <StepsForm<{
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
          console.log('formRef', formRef.current?.getFieldsValue());
          setModuleProps(formRef.current?.getFieldsValue());
          return true;
        }}
      >
        {moduleConfigTabContent}
      </StepsForm.StepForm>
      <StepsForm.StepForm<{
        checkbox: string;
      }>
        layout="horizontal" // 设置 StepForm 的 layout 属性
        name="checkbox"
        title="数据库属性"
        stepProps={{
          description: '这里填入运维参数',
        }}
        onFinish={async (values) => {
          console.log('values', values);
          dbConfigTabRef.current.formRef.current.submit()
          console.log(dbConfigTabRef.current.formRef.current.getFieldsValue());
          let res = false;
          await dbConfigTabRef.current?.validate().then((info: any) => {
            console.log('info', info);
            console.log('genApiDomainDO', dbConfigTabRef.current.formRef.current.getFieldsValue());
            setGenApiDomainDO(info);
            res = true;
          }).catch((error: any) => {
              console.log('error', error);
              res = false;
            });
          return res;
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
          const res = await formConfigTabRef?.current?.validate();
          console.log('res', res);
          if (res.success) {
            setGenViewFormDO({ columns: res.data });
          }
          return res.success;
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
          const res = await tableConfigTabRef?.current?.validate();
          console.log('res', res);
          if (res.success) {
            setGenViewTableDO({ columns: res.data });
          }
          return res.success;
        }}
      >
        {genApiDomainDO.domainFields ? tableConfigTabContent : '333'}
      </StepsForm.StepForm>
    </StepsForm>
  );

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
      children: <CodeTree moduleDO={moduleDO} codeStrs={codeStrs} />,
    },
  ];

  return (
    <>
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
          plugins={['line-numbers']}
        />
      </PageContainer>
      <Drawer title="Basic Drawer" placement="bottom" closable={true} height="100vh" onClose={() => { }} open={true}>
        <Tabs defaultActiveKey="1" tabPosition="left" items={collectItems} onChange={onMainChange} />
      </Drawer>
    </>
  );
};
export default GeneratePage;
