import type { LogEndpointData } from './data';
import * as YuApi from '@/utils/yuApi';
import { yuUrlSystem } from '@/utils/yuUrl';
import type { SortOrder } from 'antd/lib/table/interface';

const logEndpointUrl = yuUrlSystem('/logEndpoint');

/** 获取规则列表 GET */
export async function queryLogEndpoint(
  params?: any,
  sort?: Record<string, SortOrder>
) {
  return YuApi.queryPage<LogEndpointData>(logEndpointUrl, params, sort);
}

export async function getLogEndpoint(id: number, cb: any) {
  return YuApi.getById(id, logEndpointUrl).then(res => cb(res))
}