import * as YuApi from '@/utils/yuApi';
import { yuUrlSystem } from '@/utils/yuUrl';

const virtualDeptUrl = yuUrlSystem('/virtualDept');

/** 获取规则列表 GET /api/rule */
export async function queryVirtualDept(
  params?: any
) {
  return YuApi.queryPage<API.VirtualDept>(virtualDeptUrl, params);
}

export async function getVirtualDept(id: number, cb: any) {
  return YuApi.getById(virtualDeptUrl, id).then(res => cb(res))
}

/** 更新用户 PUT /api_sy/virtualDept */
export async function updateVirtualDept(record: API.VirtualDept) {
  return YuApi.update<API.VirtualDept>(virtualDeptUrl, record);
}

/** 新建用户 */
export async function addVirtualDept(record: API.VirtualDept) {
  return YuApi.add<API.VirtualDept>(virtualDeptUrl, record);
}

/** 删除规则 DELETE /api_sy/virtualDept */
export async function deleteVirtualDept(id: string | number) {
  return YuApi.deleteById(virtualDeptUrl, id)
}

export async function enableVirtualDept(id: string) {
  return YuApi.update(`${virtualDeptUrl}/${id}/enable`)
}

export async function disableVirtualDept(id: string) {
  return YuApi.update(`${virtualDeptUrl}/${id}/disable`)
}