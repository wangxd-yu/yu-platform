import type { EndpointData } from './data';
import * as YuApi from '@/utils/yuApi';
import { yuUrlSystem } from '@/utils/yuUrl';
import type { SortOrder } from 'antd/lib/table/interface';

const endpointUrl = yuUrlSystem('/endpoint');

/** 获取规则列表 GET */
export async function queryEndpoint(
  params?: any,
  sort?: Record<string, SortOrder>
) {
  return YuApi.queryPage<EndpointData>(`${endpointUrl}`, params, sort);
}

export async function getEndpoint(id: number, cb: any) {
  return YuApi.getById(endpointUrl, id).then(res => cb(res))
}

/** 更新端点 PUT */
export async function updateEndpoint(record: EndpointData) {
  return YuApi.update<EndpointData>(endpointUrl, record);
}

/** 新建端点 */
export async function addEndpoint(record: EndpointData) {
  return YuApi.add<EndpointData>(endpointUrl, record);
}

/** 删除规则 DELETE */
export async function deleteEndpoint(id: string | number) {
  return YuApi.deleteById(endpointUrl, id)
}

export async function accessEnable(id: string) {
  return YuApi.update(`${endpointUrl}/${id}:accessEnable`)
}

export async function accessDisable(id: string) {
  return YuApi.update(`${endpointUrl}/${id}:accessDisable`)
}

export async function logEnable(id: string) {
  return YuApi.update(`${endpointUrl}/${id}:logEnable`)
}

export async function logDisable(id: string) {
  return YuApi.update(`${endpointUrl}/${id}:logDisable`)
}