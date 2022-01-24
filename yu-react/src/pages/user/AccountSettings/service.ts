import { request } from 'umi';
import type { CurrentUser, GeographicItemType } from './data';
import * as YuApi from '@/utils/yuApi';
import { yuUrlSystem } from '@/utils/yuUrl';

const userUrl = yuUrlSystem('/user');

export async function queryCurrent(): Promise<{ data: CurrentUser }> {
  return request('/api/accountSettingCurrentUser');
}

export async function queryProvince(): Promise<{ data: GeographicItemType[] }> {
  return request('/api/geographic/province');
}

export async function queryCity(province: string): Promise<{ data: GeographicItemType[] }> {
  return request(`/api/geographic/city/${province}`);
}

export async function query() {
  return request('/api/users');
}

/** 更新用户 PUT /api_sy/user */
export async function updateBaseInfo(record: any) {
  return YuApi.update<any>(userUrl + "/updateBaseInfo", record);
}
