import type { DeptData, DeptRoleData } from './data';
import * as YuApi from '@/utils/yuApi';
import { request } from 'umi';
import { yuUrlSystem } from '@/utils/yuUrl';

const deptUrl = yuUrlSystem('/dept');

/** 获取规则列表 GET /api/rule */
export async function queryDeptTree(params: any) {
  return YuApi.queryList<DeptData>(`${deptUrl}/tree`, params);
}

export async function queryDept(params: any) {
  return YuApi.queryList<DeptData>(deptUrl, params);
}

export async function queryDeptRoles(id: string) {
  return YuApi.queryList<DeptRoleData>(`${deptUrl}/${id}/roles`);
}

/** 部门 新增绑定角色 */
export async function addDeptRoles(params: { deptId: string, roleIds: string[] }) {
  return YuApi.add<string[]>(`${deptUrl}/${params.deptId}/roles`, params.roleIds);
}

/** 部门 删除绑定角色 */
export async function batchDeleteDeptRoles(params: { deptId: string, roleIds: string[] }) {
  return YuApi.batchDelete(`${deptUrl}/${params.deptId}/roles:batchDelete`, params.roleIds);
}

export async function getDept(id: number) {
  return YuApi.getById<DeptData>(deptUrl, id)
}

/** 更新部门 PUT /api_sy/dept */
export async function updateDept(record: DeptData) {
  return YuApi.update<DeptData>(deptUrl, record);
}

/** 新建部门 */
export async function addDept(record: DeptData) {
  return YuApi.add<DeptData>(deptUrl, record);
}

/** 删除规则 DELETE /api_sy/dept */
export async function deleteDept(id: string | number) {
  return YuApi.deleteById(deptUrl, id)
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

/** 
 * 查询 当前部门 可用的 部门类型 (默认查询根节点 0 )
 * GET /api_sy/deptType/deptId:{deptId} 
 * */
export async function getDeptTypesByDeptId(params: { deptId: string }) {
  return request(yuUrlSystem(`/deptType/deptId:${params.deptId}`), {
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

export async function moveDept(params: { targetId: string, sourceIds: string[] }) {
  return YuApi.update(`${deptUrl}/${params.targetId}:moveIn`, params.sourceIds)
}
