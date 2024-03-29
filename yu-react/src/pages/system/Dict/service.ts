// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import type { Dict, DictItem } from './data';
import * as YuApi from '@/utils/yuApi';

const dictUrl = '/dict';
const dictItemUrl = '/dictDetail';

/** 获取规则列表 GET /api/rule */
export async function queryDict(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  }
) {
  return YuApi.queryPage<Dict>(dictUrl, params);
}

/** 获取规则列表 GET /api_sy/dictDetail */
export async function queryDictItem(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  }
) {
  return YuApi.queryPage<DictItem>(dictItemUrl, params);
}

export async function getDict(id: number) {
  return request(`/dict/${id}`, {
    method: 'GET',
  });
}

/** 更新字典 PUT /api_sy/dict */
export async function updateDict(record: Dict) {
  return YuApi.update<Dict>(dictUrl, record);
}

/** 更新字典 PUT /api_sy/dict */
export async function updateDictItem(record: DictItem) {
  return YuApi.update<DictItem>(dictItemUrl, record);
}

/** 新建字典 */
export async function addDict<Dict>(record: Dict) {
  return YuApi.add<Dict>(dictUrl, record);
}

/** 新建字典条目 */
export async function addDictItem<DictItem>(record: DictItem) {
  return YuApi.add<DictItem>(dictItemUrl, record);
}

/** 删除规则 DELETE /api_sy/dict */
export async function deleteDict(id: string | number) {
  return YuApi.deleteById(dictUrl, id)
}

export async function deleteDictItem(id: string | number) {
  return YuApi.deleteById(dictItemUrl, id)
}
