import React from 'react';
import type { MenuDataItem } from '@ant-design/pro-layout';
import * as allIcons from '@ant-design/icons';

// FIX从接口获取菜单时icon为string类型
const fixMenuItemIcon = (menus: MenuDataItem[], iconType = 'Outlined'): MenuDataItem[] => {
  const menusRtn = new Array;
  menus.forEach((item: MenuDataItem) => {
    const menuRtn = item;
    const { icon, children } = item;
    if (typeof icon === 'string') {
      const fixIconName = icon.slice(0, 1).toLocaleUpperCase() + icon.slice(1) + iconType;
      menuRtn.icon = React.createElement(allIcons[fixIconName] || allIcons[icon]);
    }
    if (children && children.length > 0) {
      menuRtn.children = fixMenuItemIcon(children)
    }
    menusRtn.push(menuRtn);
  });
  return menusRtn;
};

export default fixMenuItemIcon;