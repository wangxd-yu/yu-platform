export type DeptData = {
  id?: string;
  name?: string;
  pid?: string;
  pName?: string;
  typeId?: string;
  sort?: number;
  createTime?: number;
  children?: DeptData[];
};

export type DeptRoleData = {
  deptId?: string;
  roleId?: string;
  roleName?: string;
  roleRemark?: string;
  bindTime?: string;
};


export type TableListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type TableListData = {
  list: TableListItem[];
  pagination: Partial<TableListPagination>;
};

export type TableListParams = {
  status?: string;
  name?: string;
  desc?: string;
  key?: number;
  pageSize?: number;
  currentPage?: number;
  filter?: Record<string, any[]>;
  sorter?: Record<string, any>;
};
