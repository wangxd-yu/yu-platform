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

const transferListParams = (params: any) => {
  const paramsRtn: any = { ...params, yuRtn: 'LIST' };
  if (paramsRtn && paramsRtn.current) delete paramsRtn.current;
  if (paramsRtn && paramsRtn.pageSize) delete paramsRtn.pageSize;
  return paramsRtn;
};

const transferTreeParams = (params: any) => {
  const paramsRtn: any = { ...params, yuRtn: 'TREE' };
  if (paramsRtn && paramsRtn.current) delete paramsRtn.current;
  if (paramsRtn && paramsRtn.pageSize) delete paramsRtn.pageSize;
  return paramsRtn;
};

const queryMulti = <T> (pa: {
  url: string,
  params?: {
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: Record<string, any>
}, cb: (params: any) => any) => {
  return request<{
    data: T[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  }>(pa.url, {
    method: 'GET',
    params: cb(pa.params),
    ...(pa.options || {}),
  });
};

/** 查询请求 */
export async function queryPage<T>(
  url: string,
  params: {
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: Record<string, any>,
) {
  return queryMulti<T>({ url, params, options }, transferPageParams);
}

export async function queryList<T>(
  url: string,
  params?: {
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: Record<string, any>,
) {
  return queryMulti<T>({ url, params, options }, transferListParams);
}

export async function queryTree<T>(
  url: string,
  params?: {
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: Record<string, any>,
) {
  return queryMulti<T>({ url, params, options }, transferTreeParams);
}

/** 查询请求 */
export async function get<T>(url: string) {
  return request<T>(`${url}`, {
    method: 'GET'
  });
}

/** 查询请求-根据id */
export async function getById<T>(id: string | number, url: string) {
  return request<T>(`${url}/${id}`, {
    method: 'GET'
  });
}

/** 新增请求 */
export async function add<T>(record: T, url: string) {
  return request<T>(url, {
    method: 'POST',
    data: record,
  });
}

/** 更新请求 */
export async function update<T>(record: T, url: string) {
  return request<T>(url, {
    method: 'PUT',
    data: record,
  });
}

/** 删除请求 */
export async function deleteById(id: string | number, url: string) {
  return request<Record<string, any>>(`${url}/${id}`, {
    method: 'DELETE'
  });
}