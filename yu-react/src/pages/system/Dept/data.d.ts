export type DeptData = {
  id?: string;
  name?: string;
  no?: string;
  pno?: string;
  pName?: string;
  typeId?: string;
  sort?: number;
  createTime?: number;
  children?: DeptData[];
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
