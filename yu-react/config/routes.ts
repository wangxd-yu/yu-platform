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
    // redirect: '/system/menu',
    routes: [
      {
        name: '字典管理',
        icon: 'smile',
        path: '/system/dict',
        component: './system/Dict',
        layout: {
          hideFooter: true,
        },
      },
      {
        name: '菜单管理',
        icon: 'smile',
        path: '/system/menu',
        component: './system/Menu',
        layout: {
          hideFooter: true,
        },
      },
      {
        name: '部门管理',
        icon: 'smile',
        path: '/system/dept',
        component: './system/Dept',
        layout: {
          hideMenu: false,
          hideNav: false,
          hideHeader: true,
          hideFooter: true,
        },
      },
      {
        name: '虚拟部门',
        icon: 'smile',
        path: '/system/virtualDept',
        component: './system/VirtualDept',
        layout: {
          hideMenu: false,
          hideNav: false,
          hideHeader: true,
          hideFooter: true,
        },
      },
      {
        name: '部门管理-A',
        icon: 'smile',
        path: '/system/deptComplete',
        component: './system/DeptComplete',
        headerRender:false,
        layout: {
          hideMenu: false,
          hideNav: false,
          hideHeader: true,
          hideFooter: true,
        },
      },
      {
        name: '角色管理',
        icon: 'smile',
        path: '/system/role',
        component: './system/Role',
        layout: {
          hideFooter: true,
        },
      },
      {
        name: '用户管理',
        icon: 'smile',
        path: '/system/user',
        component: './system/User',
        footerRender: false,
        layout: {
          hideFooter: true,
        },
      },
      {
        name: '端点管理',
        icon: 'smile',
        path: '/system/endpoint',
        component: './system/Endpoint',
        layout: {
          hideFooter: true,
        },
      },
    ],
  },
  {
    path: '/log',
    name: '日志管理',
    icon: 'smile',
    routes: [
      {
        name: '登录日志',
        path: '/log/login',
        component: './log/LoginLog',
        layout: {
          hideMenu: false,
          hideNav: false,
          hideFooter: true,
        },
      },
      {
        name: '接口日志',
        path: '/log/endpoint',
        component: './log/EndpointLog',
        layout: {
          hideFooter: true,
        },
      },
    ],
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    icon: 'smile',
    path: '/accountsettings',
    component: './user/AccountSettings',
  },
  {
    component: './404',
  },
];
