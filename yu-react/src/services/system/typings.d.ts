// @ts-ignore
// /* eslint-disable */

declare namespace API {
    type VirtualDept = {
        id?: string;
        pid?: string;
        type?: string;  //类型：0：部门；1：人员
        sort: string;
        name: string;
        fullName: string;
        enabled?: string;
        subCount?: string;
    } & Record;
}