import * as YuApi from '@/utils/yuApi';
import type { SortOrder } from 'antd/lib/table/interface';
import type { CodeTemplate } from './data.d.ts';

const url = '/genCodeTemplate';

/** 查询 */
export async function queryCodeTemplate(params?: any, sort?: Record<string, SortOrder>) {
  return YuApi.queryPage<CodeTemplate>(url, params, sort);
}

/** 新建 */
export async function addCodeTemplate(record: CodeTemplate) {
  return YuApi.add<CodeTemplate>(url, record);
}
/** 更新 PUT  */
export async function updateCodeTemplate(record: CodeTemplate) {
  return YuApi.update<CodeTemplate>(url, record);
}

/** 更新 PUT  */
export async function updateCatalog(id: string, catalog: string) {
  return YuApi.update<any>(url + '/updateCatalog/' + id, {"catalog" : catalog});
}

/** 删除 DELETE */
export async function deleteCodeTemplate(id: string | number) {
  return YuApi.deleteById(url, id);
}
