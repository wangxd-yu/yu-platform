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