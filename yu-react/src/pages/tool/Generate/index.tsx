import React, { useEffect } from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';
import 'prismjs/plugins/line-numbers/prism-line-numbers';
import 'prismjs/plugins/line-numbers/prism-line-numbers.css'

import 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import PrismCode from '@/components/Yu/PrismCode';

const GeneratePage: React.FC<any> = () => {

  // 高亮所有代码
  useEffect(() => {
    Prism.highlightAll();
  }, []);

  return (
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
  );
};
export default GeneratePage;
