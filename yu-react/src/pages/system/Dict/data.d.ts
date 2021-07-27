export type TableListItem = {
  id: string;
  name: string;
  code: string;
  remark: string;
  createTime: string;
  updateTime: string;
};

export type DictItem = {
  id: string;
  dictId: string;
  name: string;
  code: string;
  sort: number;
  createTime: string;
  updateTime: string;
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
