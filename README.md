## yu-platform 多租户管理平台

### 介绍

#### **演示环境地址**：[http://117.50.173.203/yu](http://117.50.173.203/yu)

* 租户列表：1001、1002、1003
* 账号密码：
  * 租户1001：visitor/123456
  * 租户1002：admin/123456、visitor/123456

### 软件架构

### 安装教程

#### 前端

* 程序目录：	`yu-react`

* 安装

  ```shell
  yarn安装
  	yarn
  npm安装
  	npm install
  ```

* 运行

  ```shell
  yarn运行:
  	yarn run start
  npm运行:
  	npm run start
  ```
#### 后端

* 程序目录：`yu-api`

* maven安装

  ```shell
  mvn install
  ```

* 数据库

  ```
  配置文件：
  	yu-api/yu-alone/admin-base/src/main/resources/application-alone.yml
  新建数据库名称：
  	yu_alone
  修改账号、密码
  	系统运行使用
          spring.datasource.username
          spring.datasource.password
      系统初始化使用
          spring.sql.init.username
          spring.sql.init.password
  ```

  > 注：初始化建立完数据库后，将`spring.sql.init.mode` 修改为`never`

### 特性

### 演示图

| <img src="https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%86.png" alt="image-20211105234127707"  /> | ![image-20211105235054107](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235054107.png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image-20211105234857111](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105234857111.png) | ![image-20211105235007901](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235007901.png) |
| ![image-20211105235141906](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235141906.png) | ![image-20211105235156140](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235156140.png) |
| ![image-20211105235521353](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235521353.png) | ![image-20211105235538052](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235538052.png) |
| ![image-20211105235557388](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235557388.png) | ![image-20211105235619098](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235619098.png) |
| ![image-20211105235631395](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235631395.png) | ![image-20211105235721165](https://gitee.com/wangxd-yu/images/raw/master/yu-platform/image-20211105235721165.png) |

