export type MenuData = {
  id: string;
  name: string;
  type: string;
  icon: string;
  sort: number;
  path: string;
  component: string;
  permission: string;
  createTime: number;
  children?: MenuData[];
};
