import type { MenuData } from './data';
import * as YuApi from '@/utils/yuApi';

const menuUrl = '/api_sy/menu';

/** 获取规则列表 GET /api/rule */
export async function queryMenu(
  params: any,
  options?: Record<string, any>,
) {
  return YuApi.queryListReq<MenuData>(menuUrl, params, options);
}

export async function getMenu(id: number) {
  return YuApi.getReq(id, menuUrl)
}

/** 更新菜单 PUT /api_sy/menu */
export async function updateMenu(record: MenuData) {
  return YuApi.updateReq<MenuData>(record, menuUrl);
}

/** 新建菜单 */
export async function addMenu<MenuData>(record: MenuData) {
  return YuApi.addReq<MenuData>(record, menuUrl);
}

/** 删除规则 DELETE /api_sy/menu */
export async function deleteMenu(id: string | number) {
  return YuApi.deleteReq(id, menuUrl)
}
