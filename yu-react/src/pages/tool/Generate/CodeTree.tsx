import React, { useEffect, useState } from 'react';
import { Col, Row, Tree } from 'antd';
import type { DataNode, DirectoryTreeProps } from 'antd/es/tree';
import { request } from 'umi';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';
import 'prismjs/plugins/line-numbers/prism-line-numbers';
import 'prismjs/plugins/line-numbers/prism-line-numbers.css'

import 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import PrismCode from '@/components/Yu/PrismCode';

const { DirectoryTree } = Tree;

const treeData: DataNode[] = [
  {
    title: '后端',
    key: '0-0',
    children: [
      {
        title: 'controller', key: '0-0-0', children: [
          { title: 'Controller.java', key: '0-0-0-0', isLeaf: true },
        ]
      },
      {
        title: 'service', key: '0-0-1', children: [
          { title: 'Service.java', key: '0-0-1-0', isLeaf: true },
          { title: 'ServiceImpl.java', key: '0-0-1-1', isLeaf: true },
        ]
      },
      {
        title: 'repository', key: '0-0-2', children: [
          { title: 'Repository.java', key: '0-0-2-0', isLeaf: true },
        ]
      },
      {
        title: 'domain', key: '0-0-3', children: [
          { title: 'DO.java', key: '0-0-3-0', isLeaf: true },
        ]
      },
      {
        title: 'query', key: '0-0-4', children: [
          { title: 'Query.java', key: '0-0-4-0', isLeaf: true },
        ]
      },
    ],
  },
  {
    title: '前端',
    key: '0-1',
    children: [
      {
        title: 'compontents', key: '0-1-0', children: [
          { title: 'Form.tsx', key: '0-1-0-0', isLeaf: true },
        ]
      },
      { title: 'data.d.ts', key: '0-1-1', isLeaf: true },
      { title: 'service.tsx', key: '0-1-2', isLeaf: true },
      { title: 'index.tsx', key: '0-1-3', isLeaf: true },
    ],
  },
];

const CodeTree = (props: { moduleDO: any, codeStrs: any[] }) => {
  const [codeStr, setCodeStr] = useState<string>("");
  /* 
    useEffect(() => {
      request('getTest1', {
        method: 'POST',
        data: props.moduleDO,
      }).then(res => console.log(res));
    }) */
  const onSelect: DirectoryTreeProps['onSelect'] = (keys, info) => {
    console.log('Trigger Select', keys, info);
    if(info.node.isLeaf) {
      let fileName = String(info.node.title)?.split(".")[0]
      setCodeStr(props.codeStrs.find(item => item.name === fileName)?.content)
    }
  };

  const onExpand: DirectoryTreeProps['onExpand'] = (keys, info) => {
    console.log('Trigger Expand', keys, info);
  };

  return (
    <Row>
      <Col span={6}> <DirectoryTree
        multiple
        defaultExpandAll
        onSelect={onSelect}
        onExpand={onExpand}
        treeData={treeData}
      /></Col>
      <Col span={18}>
        <PrismCode
          style={{ width: '100%', height: '85vh' }}
          code={codeStr}
          language="js"
          plugins={["line-numbers"]}
        />
      </Col>
    </Row>

  );
};

export default CodeTree;