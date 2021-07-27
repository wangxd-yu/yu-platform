// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import type { DictItem, TableListItem } from './data';
import {transferPageParams} from '@/utils/requestUtil';

/** 获取规则列表 GET /api/rule */
export async function dict(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<{
    data: TableListItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  }>('/api_sy/dict', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api_sy/dictDetail */
export async function dictItem(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<{
    data: DictItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  }>('/api_sy/dictDetail', {
    method: 'GET',
    params: transferPageParams(params),
    ...(options || {}),
  });
}

export async function getDict(id: number) {
  return request(`/api_sy/dict/${id}`, {
    method: 'GET',
  });
}

/** 更新字典 PUT /api_sy/dict */
export async function updateDict(record: TableListItem) {
  return request<TableListItem>('/api_sy/dict', {
    method: 'PUT',
    data: record,
  });
}

/** 新建字典 POST /api_sy/dict */
export async function addDict(record: TableListItem) {
  return request<TableListItem>('/api_sy/dict', {
    method: 'POST',
    data: record,
  });
}

/** 删除规则 DELETE /api_sy/dict */
export async function removeDict(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api_sy/dict', {
    method: 'DELETE',
    ...(options || {}),
  });
}
