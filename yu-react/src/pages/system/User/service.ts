import type { UserData } from './data';
import * as YuApi from '@/utils/yuApi';

const userUrl = '/user';

/** 获取规则列表 GET /api/rule */
export async function queryUser(
  params?: any
) {
  return YuApi.queryPage<UserData>(userUrl, params);
}

export async function getUser(id: number, cb: any) {
  return YuApi.getById(userUrl, id).then(res => cb(res))
}

/** 更新用户 PUT /api_sy/user */
export async function updateUser(record: UserData) {
  return YuApi.update<UserData>(userUrl, record);
}

/** 新建用户 */
export async function addUser(record: UserData) {
  return YuApi.add<UserData>(userUrl, record);
}

/** 删除规则 DELETE /api_sy/user */
export async function deleteUser(id: string | number) {
  return YuApi.deleteById(userUrl, id)
}

export async function enableUser(id: string) {
  return YuApi.update(`${userUrl}/${id}/enable`)
}

export async function disableUser(id: string) {
  return YuApi.update(`${userUrl}/${id}/disable`)
}