export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {
            path: '/user/login',
            component: './user/Login',
          },
        ],
      },
    ],
  },
  {
    path: '/welcome',
    icon: 'smile',
    component: './Welcome',
  },
  {
    path: '/admin',
    icon: 'crown',
    access: 'canAdmin',
    component: './Admin',
    routes: [
      {
        path: '/admin/sub-page',
        icon: 'smile',
        component: './Welcome',
      },
    ],
  },
  {
    path: '/system',
    name: '系统管理',
    icon: 'crown',
    // access: 'canAdmin',
    routes: [
      {
        name: '字典管理',
        icon: 'smile',
        path: '/system/dict',
        component: './system/Dict',
      },
      {
        name: '菜单管理',
        icon: 'smile',
        path: '/system/menu',
        component: './system/Menu',
      },
    ],
  },
  {
    name: '表格管理',
    icon: 'table',
    path: '/list',
    component: './TableList',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    name: '查询表格',
    icon: 'smile',
    path: '/ListTableList',
    component: './ListTableList',
  },
  {
    component: './404',
  },
];
