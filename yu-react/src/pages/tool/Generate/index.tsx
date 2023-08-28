import React, { useEffect, useState } from 'react';
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
import { Drawer, Tabs } from 'antd';
import type { ApiDomainField, ViewFormColumn, ViewTableQuery, ViewTableColumn } from './data';
import DbConfigTab from './DbConfigTab';
import FormConfigTab from './FormConfigTab';
import { ProFormText } from '@ant-design/pro-form';
import TableConfigTab from './TableConfigTab';
import ModuleConfigTab from './ModuleConfigTab';

const onChange = (key: string) => {
  console.log(key);
};

const GeneratePage: React.FC<any> = () => {
  const [apiDomainFields, setApiDomainFields] = useState<ApiDomainField[]>([]);
  const [viewFormColumns, setViewFormColumns] = useState<ViewFormColumn[]>([]);
  const [viewTableQuerys, setViewTableQuerys] = useState<ViewTableQuery[]>([]);
  const [viewTableColumns, setViewTableColumns] = useState<ViewTableQuery[]>([]);

  const moduleConfigTabContent = (
    <ModuleConfigTab
      onValuesChange={setApiDomainFields}
    />
  );

  const dbConfigTabContent = (
    <DbConfigTab
      dataSource={apiDomainFields}
      onValuesChange={setApiDomainFields}
    />
  );
  const formConfigTabContent = (
    <FormConfigTab
      transferDataSource={apiDomainFields}
      onValuesChange={(values) => console.log("values", values)} />
  );

  const TableConfigTabContent = (
    <TableConfigTab
      transferDataSource={apiDomainFields}
      onValuesChange={(values) => console.log("values", values)} />
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
      label: `查询字段`,
      children: TableConfigTabContent,
    },
  ];

  const collectItems: TabsProps['items'] = [
    {
      key: '1',
      label: `配置`,
      children: (<Tabs defaultActiveKey="1" items={items} onChange={onChange} />),
    },
    {
      key: '2',
      label: `预览`,
      children: (<></>),
    }
  ]

  // 高亮所有代码
  useEffect(() => {
    Prism.highlightAll();
  }, []);

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
      <Tabs defaultActiveKey="1" tabPosition="left" items={collectItems} onChange={onChange} />
    </Drawer>

  </>
  );
};
export default GeneratePage;
