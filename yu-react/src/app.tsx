import Footer from '@/components/Footer';
import { LinkOutlined } from '@ant-design/icons';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import { SettingDrawer } from '@ant-design/pro-components';
import type { RunTimeLayoutConfig } from '@umijs/max';
import { history, Link } from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import { AvatarDropdown, AvatarName } from './components/RightContent/AvatarDropdown';
import { errorConfig } from './requestErrorConfig';
import { currentUser as queryCurrentUser } from './services/ant-design-pro/api';
import { getAuthToken, isTokenEfective } from './utils/AuthUtil';
const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/user/login';

import * as YuApi from '@/utils/yuApi';
import fixMenuItemIcon from './utils/fixMenuItemIcon';
import NoticeIconView from './components/NoticeIcon';

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: API.CurrentUser;
  loading?: boolean;
  fetchUserInfo?: () => Promise<API.CurrentUser | undefined>;
}> {
  const fetchUserInfo = async () => {
    try {
      const msg = await queryCurrentUser();
      return msg;
    } catch (error) {
      history.push(loginPath);
    }
    return undefined;
  };
  // 如果不是登录页面，执行
  if (history.location.pathname.indexOf(loginPath) < 0 && getAuthToken()) {
    if (isTokenEfective()) {
      const currentUser = await fetchUserInfo();
      return {
        fetchUserInfo,
        currentUser,
        settings: defaultSettings as Partial<LayoutSettings>,
      };
    } else {
      localStorage.clear()
    }
  }
  return {
    fetchUserInfo,
    settings: defaultSettings as Partial<LayoutSettings>,
  };
}

const findFirstPageMenu = (menuData: any) => {
  for (const menu of menuData) {
    if (menu.routes && menu.routes.length > 0) {
      const firstSubMenu = findFirstPageMenu(menu.routes);
      if (firstSubMenu) {
        return firstSubMenu;
      }
    } else if (menu.component) {
      // Check if the component is not null
      return menu;
    }
  }
  return null;
};

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {
  return {
    //actionsRender: () => [<Question key="doc" />, <SelectLang key="SelectLang" />],
    //actionsRender: () => [initialState?.currentUser? <NoticeIconView key="niv" />: ''],
    avatarProps: {
      src: `/${BASE_URL_PREFIX}/${initialState?.currentUser?.portraitUrl}`,
      title: <AvatarName />,
      render: (_, avatarChildren) => {
        return <AvatarDropdown menu>{avatarChildren}</AvatarDropdown>;
      },
    },
    waterMarkProps: {
      content: initialState?.currentUser?.name,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      const { location } = history;
      // 如果没有登录，重定向到 login
      if (!isTokenEfective() && location.pathname !== loginPath) {
        history.push(loginPath);
      } else if (isTokenEfective() && !initialState?.currentUser){
        history.push("/")
      } 
    },
    layoutBgImgList: [
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/D2LWSqNny4sAAAAAAAAAAAAAFl94AQBr',
        left: 85,
        bottom: 100,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/C2TWRpJpiC0AAAAAAAAAAAAAFl94AQBr',
        bottom: -68,
        right: -45,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/F6vSTbj8KpYAAAAAAAAAAAAAFl94AQBr',
        bottom: 0,
        left: 0,
        width: '331px',
      },
    ],
    links: isDev
      ? [
          <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
            <LinkOutlined />
            <span>OpenAPI 文档</span>
          </Link>,
        ]
      : [],
    menuDataRender: (menuItemProps) => {
      return fixMenuItemIcon(menuItemProps)
    },
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
          <SettingDrawer
            disableUrlParams
            enableDarkTheme
            settings={initialState?.settings}
            onSettingChange={(settings) => {
              setInitialState((preInitialState) => ({
                ...preInitialState,
                settings,
              }));
            }}
          />
        </>
      );
    },
    
    menu: {
      locale: false,
      // 每当 initialState?.currentUser?.userid 发生修改时重新执行 request
      params: {
        username: initialState?.currentUser?.username,
      },
      request: async (params, defaultMenuData) => {
        // initialState.currentUser 中包含了所有用户信息
        // const menuData = await fetchMenuData();
        //用户未登录直接返回
        if(!initialState?.currentUser) {
          return null;
        }
        const menuData = await YuApi.queryList(`/menu/build`);

        // 在这里处理菜单数据，并决定是否跳转到第一个有页面的菜单项
        /* const firstPageMenu = findFirstPageMenu(menuData);
        if (firstPageMenu && firstPageMenu.path) {
          // 设置默认路由
          console.log("firstPageMenu.path----", firstPageMenu.path)
          history.push(firstPageMenu.path);
        } */

        return menuData;
      },
      defaultOpenAll: true,
    },
    ...initialState?.settings,
  };
};

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request = {
  ...errorConfig,
};