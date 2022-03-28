export type Dict = {
  id: string;
  name: string;
  code: string;
  remark: string;
  createTime?: string;
  updateTime?: string;
};

export type DictItem = {
  id: string;
  dictId: string;
  name: string;
  code: string;
  sort: number;
  createTime?: string;
  updateTime?: string;
}