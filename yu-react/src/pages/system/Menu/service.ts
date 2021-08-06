// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import type { MenuData } from './data';
import * as YuApi from '@/utils/yuApi';

const menuUrl = '/api_sy/menu';

/** 获取规则列表 GET /api/rule */
export async function queryMenu(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return YuApi.queryReq<MenuData>(menuUrl, params, options);
}

export async function getMenu(id: number) {
  return request(`/api_sy/menu/${id}`, {
    method: 'GET',
  });
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
