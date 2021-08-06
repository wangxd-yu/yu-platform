import { request } from 'umi';
// 转换分页参数 
export const transferPageParams = (params: any) => {
    const paramsRtn: any = {
        ...params,
        page: params.current - 1,
        size: params.pageSize
    }
    delete paramsRtn.current;
    delete paramsRtn.pageSize;
    return paramsRtn;
};

/** 查询请求 */
export async function queryReq<T>(
    url: string,
    params: {
      /** 当前的页码 */
      current?: number;
      /** 页面的容量 */
      pageSize?: number;
    },
    options?: Record<string, any>,
  ) {
    return request<{
      data: T[];
      /** 列表的内容总数 */
      total?: number;
      success?: boolean;
    }>(url, {
      method: 'GET',
      params: transferPageParams(params),
      ...(options || {}),
    });
  }

/** 新增请求 */
export async function addReq<T>(record: T, url: string) {
    return request<T>(url, {
        method: 'POST',
        data: record,
    });
}

/** 更新请求 */
export async function updateReq<T>(record: T, url: string) {
    return request<T>(url, {
        method: 'PUT',
        data: record,
    });
}

/** 删除请求 */
export async function deleteReq(id: string | number, url: string) {
    return request<Record<string, any>>(`${url}/${id}`, {
        method: 'DELETE'
    });
}