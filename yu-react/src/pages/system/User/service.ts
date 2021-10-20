import type { UserData } from './data';
import * as YuApi from '@/utils/yuApi';
import { yuUrlSystem } from '@/utils/yuUrl';

const userUrl = yuUrlSystem('/user');

/** 获取规则列表 GET /api/rule */
export async function queryUser(
  params?: any,
  options?: Record<string, any>,
) {
  return YuApi.queryPage<UserData>(userUrl, params, options);
}

export async function getUser(id: number, cb: any) {
  return YuApi.getById(id, userUrl).then(res => cb(res))
}

/** 更新用户 PUT /api_sy/user */
export async function updateUser(record: UserData) {
  return YuApi.update<UserData>(userUrl, record);
}

/** 新建用户 */
export async function addUser(record: UserData) {
  return YuApi.add<UserData>(record, userUrl);
}

/** 删除规则 DELETE /api_sy/user */
export async function deleteUser(id: string | number) {
  return YuApi.deleteById(id, userUrl)
}
