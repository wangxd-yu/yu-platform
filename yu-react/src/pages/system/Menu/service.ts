import type { MenuData } from './data';
import * as YuApi from '@/utils/yuApi';

const menuUrl = '/api_sy/menu';

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
  return YuApi.update<MenuData>(record, menuUrl);
}

/** 新建菜单 */
export async function addMenu<MenuData>(record: MenuData) {
  return YuApi.add<MenuData>(record, menuUrl);
}

/** 删除规则 DELETE /api_sy/menu */
export async function deleteMenu(id: string | number) {
  return YuApi.deleteById(id, menuUrl)
}
