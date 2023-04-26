import type { RoleData } from './data';
import * as YuApi from '@/utils/yuApi';
import { request } from 'umi';

import type { UserData } from '../User/data';

const roleUrl = '/role';

/** 获取规则列表 GET /api/rule */
export async function queryRole(
  params: any
) {
  return YuApi.queryPage<RoleData>(roleUrl, params);
}

export async function getRole(id: number) {
  return YuApi.getById(roleUrl, id)
}

/** 更新角色 PUT /api_sy/role */
export async function updateRole(record: RoleData) {
  return YuApi.update<RoleData>(roleUrl, record);
}

/** 新建角色 */
export async function addRole(record: RoleData) {
  return YuApi.add<RoleData>(roleUrl, record);
}

/** 删除规则 DELETE /api_sy/role */
export async function deleteRole(id: string | number) {
  return YuApi.deleteById(roleUrl, id)
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


/** 获取对应角色的用户列表 */
export async function queryRoleUserPage(params: any) {
  console.log("roleId", params.roleId)
  return YuApi.queryPage<UserData>(`${roleUrl}/${params.roleId}/users`, params);
}