import type { MenuData } from './data';
import * as YuApi from '@/utils/yuApi';

import { yuUrlSystem } from '@/utils/yuUrl';
import type { EndpointData } from '../Endpoint/data';
import { request } from 'umi';

const menuUrl = yuUrlSystem('/menu');
const endpointUrl = yuUrlSystem('/endpoint')

/** 获取规则列表 GET /api/rule */
export async function queryMenu(
  params?: any,
  options?: Record<string, any>,
) {
  return YuApi.queryTree<MenuData>(menuUrl, params, options);
}

export async function getMenu(id: number) {
  return YuApi.getById(id, menuUrl)
}

/** 更新菜单 PUT /api_sy/menu */
export async function updateMenu(record: MenuData) {
  return YuApi.update<MenuData>(menuUrl, record);
}

/** 新建菜单 */
export async function addMenu(record: MenuData) {
  return YuApi.add<MenuData>(record, menuUrl);
}

/** 删除规则 DELETE /api_sy/menu */
export async function deleteMenu(id: string | number) {
  return YuApi.deleteById(id, menuUrl)
}

/** 获取规则列表 GET /api/menu/${id}/endpoints */
export async function queryMenuEndponits(
  params?: any,
  options?: Record<string, any>,
) {
  return YuApi.queryList<EndpointData>(`${menuUrl}/${params.menuId}/endpoints`, params, options);
}

export async function deleteMenuEndpoint(menuId: string | number, endpointId: string | number) {
  return request<any>(`${menuUrl}/${menuId}/endpoints/${endpointId}`, {
    method: 'delete'
  });
}

export async function saveMenuEndpoints(id: string, endpointIds: string[]) {
  return request<any>(`${menuUrl}/${id}/endpoints`, {
    method: 'POST',
    data: {endpointIds: endpointIds}
  });
 /*  return YuApi.add<{id: string, endpointIds: string[]}>({id, endpointIds}, `${menuUrl}/${id}/endpoints`); */
}

export async function queryEndponitList(
  params?: any,
  options?: Record<string, any>,
) {
  return YuApi.queryList<EndpointData>(endpointUrl, params, options);
}
