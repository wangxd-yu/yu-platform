import type { LogLoginData } from './data';
import * as YuApi from '@/utils/yuApi';
import type { SortOrder } from 'antd/lib/table/interface';

const logLoginUrl = '/logLogin';

/** 获取规则列表 GET */
export async function queryLogLogin(
  params?: any,
  sort?: Record<string, SortOrder>
) {
  return YuApi.queryPage<LogLoginData>(logLoginUrl, params, sort);
}