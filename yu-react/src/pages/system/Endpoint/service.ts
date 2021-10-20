import type { EndpointData } from './data';
import * as YuApi from '@/utils/yuApi';
import { yuUrlSystem } from '@/utils/yuUrl';

const endpointUrl = yuUrlSystem('/endpoint');

/** 获取规则列表 GET */
export async function queryEndpoint(
  params?: any,
  options?: Record<string, any>,
) {
  return YuApi.queryPage<EndpointData>(endpointUrl, params, options);
}

export async function getEndpoint(id: number, cb: any) {
  return YuApi.getById(id, endpointUrl).then(res => cb(res))
}

/** 更新端点 PUT */
export async function updateEndpoint(record: EndpointData) {
  return YuApi.update<EndpointData>(endpointUrl, record);
}

/** 新建端点 */
export async function addEndpoint(record: EndpointData) {
  return YuApi.add<EndpointData>(record, endpointUrl);
}

/** 删除规则 DELETE */
export async function deleteEndpoint(id: string | number) {
  return YuApi.deleteById(id, endpointUrl)
}

export async function enableEndpoint(id: string) {
  return YuApi.update(`${endpointUrl}/${id}/enable`)
}

export async function disableEndpoint(id: string) {
  return YuApi.update(`${endpointUrl}/${id}/disable`)
}
