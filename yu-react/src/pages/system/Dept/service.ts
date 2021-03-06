import type { DeptData } from './data';
import * as YuApi from '@/utils/yuApi';
import { request } from 'umi';
import { yuUrlSystem } from '@/utils/yuUrl';

const deptUrl = yuUrlSystem('/dept');

/** 获取规则列表 GET /api/rule */
export async function queryDept(
  params: any
) {
  return YuApi.queryList<DeptData>(deptUrl, params);
}

export async function getDept(id: string) {
  return YuApi.getById<DeptData>(id, deptUrl)
}

/** 更新部门 PUT /api_sy/dept */
export async function updateDept(record: DeptData) {
  return YuApi.update<DeptData>(deptUrl, record);
}

/** 新建部门 */
export async function addDept(record: DeptData) {
  return YuApi.add<DeptData>(record, deptUrl);
}

/** 删除规则 DELETE /api_sy/dept */
export async function deleteDept(id: string | number) {
  return YuApi.deleteById(id, deptUrl)
}

/** 
 * 查询子部门类型 (默认查询根节点 0 )
 * GET /api_sy/deptType/subTypes/{typeId} 
 * */
export async function getSubDeptTypes(params: any) {
  const typeId = params.typeId ? params.typeId : '0';
  return request(yuUrlSystem(`/deptType/subTypes/${typeId}`), {
    method: 'get'
  }).then(res => {
    return res.map((item: any) => {
      return {
        label: item.name,
        value: item.id
      }
    })
  });
}

export async function enableDept(id: string) {
  return YuApi.update(`${deptUrl}/${id}/enable`)
}

export async function disableDept(id: string) {
  return YuApi.update(`${deptUrl}/${id}/disable`)
}
