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
