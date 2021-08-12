import type { UserData } from './data';
import * as YuApi from '@/utils/yuApi';

const userUrl = '/api_sy/user';

/** 获取规则列表 GET /api/rule */
export async function queryUser(
  params?: any,
  options?: Record<string, any>,
) {
  return YuApi.queryPageReq<UserData>(userUrl, params, options);
}

export async function getUser(id: number, cb: any) {
  return YuApi.getReq(id, userUrl).then(res => cb(res))
}

/** 更新用户 PUT /api_sy/user */
export async function updateUser(record: UserData) {
  return YuApi.updateReq<UserData>(record, userUrl);
}

/** 新建用户 */
export async function addUser<UserData>(record: UserData) {
  return YuApi.addReq<UserData>(record, userUrl);
}

/** 删除规则 DELETE /api_sy/user */
export async function deleteUser(id: string | number) {
  return YuApi.deleteReq(id, userUrl)
}
