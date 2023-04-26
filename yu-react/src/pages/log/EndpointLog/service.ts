import type { LogEndpointData } from './data';
import * as YuApi from '@/utils/yuApi';
import type { SortOrder } from 'antd/lib/table/interface';

const logEndpointUrl = '/logEndpoint';

/** 获取规则列表 GET */
export async function queryLogEndpoint(
  params?: any,
  sort?: Record<string, SortOrder>
) {
  return YuApi.queryPage<LogEndpointData>(logEndpointUrl, params, sort);
}

export async function getLogEndpoint(id: number, cb: any) {
  return YuApi.getById(logEndpointUrl, id).then(res => cb(res))
}