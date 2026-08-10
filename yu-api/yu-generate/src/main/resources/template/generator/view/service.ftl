<#--定义业务类型-->
<#assign moduleType = module.genViewModuleDO.moduleName?cap_first>
<#--定义业务对象-->
<#assign moduleObject = module.genViewModuleDO.moduleName?uncap_first>
import type { ${moduleType} } from './data.d.ts';
import * as YuApi from '@/utils/yuApi';
import type { SortOrder } from 'antd/lib/table/interface';

const url = '/${moduleObject}';

/** 查询 */
export async function query${moduleType}(params?: any,sort?: Record<string, SortOrder>) {
    return YuApi.queryPage<${moduleType}>(url, params, sort);
}

/** 新建 */
export async function add${moduleType}(record: ${moduleType}) {
    return YuApi.add<${moduleType}>(url, record);
}
/** 更新 PUT  */
export async function update${moduleType}(record: ${moduleType}) {
    return YuApi.update<${moduleType}>(url, record);
}
/** 删除 DELETE */
export async function delete${moduleType}(id: string | number) {
    return YuApi.deleteById(url, id)
}