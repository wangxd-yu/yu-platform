import type { RoleData } from './data';
import * as YuApi from '@/utils/yuApi';
import { request } from 'umi';

const roleUrl = '/api_sy/role';

/** 获取规则列表 GET /api/rule */
export async function queryRole(
  params: any,
  options?: Record<string, any>,
) {
  return YuApi.queryPageReq<RoleData>(roleUrl, params, options);
}

export async function getRole(id: number) {
  return YuApi.getReq(id, roleUrl)
}

/** 更新角色 PUT /api_sy/role */
export async function updateRole(record: RoleData) {
  return YuApi.updateReq<RoleData>(record, roleUrl);
}

/** 新建角色 */
export async function addRole<RoleData>(record: RoleData) {
  return YuApi.addReq<RoleData>(record, roleUrl);
}

/** 删除规则 DELETE /api_sy/role */
export async function deleteRole(id: string | number) {
  return YuApi.deleteReq(id, roleUrl)
}

export async function saveRoleMenus(record: {roleId: string, menuIds: string[]}) {
  return request<any>(`${roleUrl}/${record.roleId}/menus`, {
    method: 'POST',
    data: {menuIds: record.menuIds}
  });
}

export async function getRoleMenus(roleId: string) {
  return request<string[]>(`${roleUrl}/${roleId}/menus`, {
    method: 'GET'
  });
}