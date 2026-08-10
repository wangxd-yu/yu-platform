import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';


import PrismCode from '@/components/Yu/PrismCode';

import React from 'react';

const formItemLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 20 },
};

const codeStr = `1111111111111`; // 表单配置

const CodeTemplateForm: React.FC<YuFormProps> = (props: YuFormProps) => {
  return (
    <YuForm {...formItemLayout} {...props}>
      <div>
        <PrismCode
          style={{ width: '100%', height: '85vh' }}
          code={codeStr}
          language="js"
          plugins={['line-numbers']}
        />
      </div>
    </YuForm>
  );
};

export default CodeTemplateForm;
