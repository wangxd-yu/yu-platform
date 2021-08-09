export type RoleData = {
  id: string;
  name: string;
  type: string;
  icon: string;
  sort: number;
  path: string;
  component: string;
  permission: string;
  createTime: number;
};

export type RoleMenu = {
  roleId: string;
  menuId: string;
}

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
