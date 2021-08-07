// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import type { Dict, DictItem } from './data';
import * as YuApi from '@/utils/yuApi';

const dictUrl = '/api_sy/dict';
const dictItemUrl = '/api_sy/dictDetail';

/** 获取规则列表 GET /api/rule */
export async function queryDict(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return YuApi.queryPageReq<Dict>(dictUrl, params, options);
}

/** 获取规则列表 GET /api_sy/dictDetail */
export async function queryDictItem(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return YuApi.queryPageReq<DictItem>(dictItemUrl, params, options);
}

export async function getDict(id: number) {
  return request(`/api_sy/dict/${id}`, {
    method: 'GET',
  });
}

/** 更新字典 PUT /api_sy/dict */
export async function updateDict(record: Dict) {
  return YuApi.updateReq<Dict>(record, dictUrl);
}

/** 更新字典 PUT /api_sy/dict */
export async function updateDictItem(record: DictItem) {
  return YuApi.updateReq<DictItem>(record, dictItemUrl);
}

/** 新建字典 */
export async function addDict<Dict>(record: Dict) {
  return YuApi.addReq<Dict>(record, dictUrl);
}

/** 新建字典条目 */
export async function addDictItem<DictItem>(record: DictItem) {
  return YuApi.addReq<DictItem>(record, dictItemUrl);
}

/** 删除规则 DELETE /api_sy/dict */
export async function deleteDict(id: string | number) {
  return YuApi.deleteReq(id, dictUrl)
}

export async function deleteDictItem(id: string | number) {
  return YuApi.deleteReq(id, dictItemUrl)
}
