<#assign formDO = module.genViewModuleDO.genViewCrudDO.genViewFormDO>
<#--定义业务类型-->
<#assign moduleType = module.genViewModuleDO.moduleName?cap_first>
import React from 'react'
import { ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';
import * as YuApi from '@/utils/yuApi';

const formItemLayout = {
labelCol: { span: 4 },
wrapperCol: { span: 20 },
}
const ${moduleType}Form: React.FC
<YuFormProps> = (props: YuFormProps) => {
    return (
    <YuForm {...formItemLayout} {...props}>
        <#if formDO.columns??>
            <#list formDO.columns as column>
                <ProFormText
                        label="${column.title}"
                        name="${column.field}"
                />
            </#list>
        </#if>
        />
    </YuForm>
    )
    }
    export default ${moduleType}Form;