import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';
import * as YuCrud from '@/utils/yuCrud';
import * as YuApi from '@/utils/yuApi';
import MonacoEditor from "react-monaco-editor";
import Handlebars from "handlebars";
import './index.less';
import React, { useEffect, useState } from 'react';
import SplitPane from 'react-split-pane';
import type { CollapseProps, MenuProps } from 'antd';
import { Button, Col, Collapse, Dropdown, Flex, Form, Input, Modal, Row, Tooltip, Tree, message } from 'antd';
import { EllipsisOutlined, FileAddOutlined, FolderAddOutlined, SearchOutlined, SettingOutlined } from '@ant-design/icons';
import type { MenuInfo } from 'rc-menu/lib/interface';
import Item from 'antd/es/descriptions/Item';
import type { DataNode } from 'antd/es/tree';
import { updateCatalog } from '../service';

const modelData = {
  "genApiModuleDO": {
      "author": "wangxd",
      "packagePath": "com.yu.module",
      "genApiDomainDO": {
    "lowerDomainName": "student",
    "upperDomainName": "Student",
          "domainFields": [
              {
                  "id": "1711118866054_id",
                  "sort": 1,
                  "name": "id",
                  "type": "String",
                  "length": 20,
                  "comment": "主键",
                  "ifNotNull": true,
                  "key": "1711118866054_id"
              },
              {
                  "id": "1711118866054_ct",
                  "sort": 2,
                  "name": "createTime",
                  "type": "Datetime",
                  "length": 3,
                  "comment": "创建时间",
                  "ifNotNull": false,
                  "key": "1711118866054_ct"
              },
              {
                  "id": "1711118866054_ut",
                  "sort": 3,
                  "name": "updateTime",
                  "type": "Datetime",
                  "length": 3,
                  "comment": "更新时间",
                  "ifNotNull": false,
                  "key": "1711118866054_ut"
              },
              {
                  "name": "name",
                  "type": "String",
                  "length": 20,
                  "comment": "姓名"
              },
              {
                  "name": "age",
                  "type": "Int",
                  "comment": "年龄",
                  "length": 3
              }
          ],
          "tableName": "t_student",
          "domainName": "student"
      }
  },
  "genViewModuleDO": {
      "moduleName": "student",
      "type": "CRUD",
      "genViewCrudDO": {
          "genViewFormDO": {
              "columns": [
                  {
                      "id": "1711118866054_id",
                      "field": "id",
                      "title": "主键",
                      "length": 20,
                      "sort": 1,
                      "search": false
                  },
                  {
                      "id": "1711118866054_ct",
                      "field": "createTime",
                      "title": "创建时间",
                      "length": 3,
                      "sort": 2,
                      "search": false
                  },
                  {
                      "id": "1711118866054_ut",
                      "field": "updateTime",
                      "title": "更新时间",
                      "length": 3,
                      "sort": 3,
                      "search": false
                  },
                  {
                      "field": "name",
                      "title": "姓名",
                      "length": 20,
                      "search": false
                  },
                  {
                      "field": "age",
                      "title": "年龄",
                      "length": 3,
                      "search": false
                  }
              ]
          },
          "genViewTableDO": {
              "columns": [
                  {
                      "id": "1711118866054_id",
                      "field": "id",
                      "title": "主键",
                      "length": 20,
                      "sort": 1,
                      "search": false
                  },
                  {
                      "id": "1711118866054_ct",
                      "field": "createTime",
                      "title": "创建时间",
                      "length": 3,
                      "sort": 2,
                      "search": false
                  },
                  {
                      "id": "1711118866054_ut",
                      "field": "updateTime",
                      "title": "更新时间",
                      "length": 3,
                      "sort": 3,
                      "search": false
                  },
                  {
                      "field": "name",
                      "title": "姓名",
                      "length": 20,
                      "search": false
                  },
                  {
                      "field": "age",
                      "title": "年龄",
                      "length": 3,
                      "search": false
                  }
              ],
              "title": "学生管理"
          }
      }
  }
}

const formItemLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 20 },
};

const CodeTemplateForm: React.FC<YuFormProps> = (props: YuFormProps) => {
  console.log("props", props);
  const { DirectoryTree } = Tree;
  // 声明一个用于存储当前选中节点的状态
  const [currentSelectDataNode, setCurrentSelectDataNode] = useState<DataNode | null>(null);
  //当前创建的类型：目录、文件
  const [currentCreateType, setCurrentCreateType] = useState<string>();   //folder 、 file
  //const [code, setCode] = useState(props?.initialValues?.code);
  const [code, setCode] = useState<string>("");
  const [genCode, setGenCode] = useState<string>();

  let template = ''
  useEffect(() => {
    try {
      template = Handlebars.compile(code)
      setGenCode(template(modelData))
    } catch (error) {
      console.log("error", error)
      setGenCode(String(error));
    }
  }, [code]);

  const options: any = {
    selectOnLineNumbers: true,
    roundedSelection: false,
    readOnly: false,
    cursorStyle: "line",
    automaticLayout: true,// 编辑器自适应页面大小
    minimap: {
      enabled: false, // 是否开启右侧代码小窗
    }
  };

  
  const [theme] = useState('vs-light');
  const [language] = useState('freemarker2');

  const [treeData, setTreeData] = useState<DataNode[]>(() => {
    try {
      // 尝试解析props.initialValues.catalog
      const parsedCatalog = JSON.parse(props.initialValues?.catalog || '[]');
      return Array.isArray(parsedCatalog) ? parsedCatalog : [];
    } catch (error) {
      console.error('树结构解析异常', error);
      return [];
    }
  });

  const [modalOpen, setModalOpen] = useState(false);
  const [modalTitle, setModalTitle] = useState("");

  const onChange = (newValue: any) => {
    console.log("onChange", newValue);
    setCode(newValue);
    console.log("code", code);
  };

  // 获取当前选中的节点
  const getCurrentSelectDataNode = () => {
    return currentSelectDataNode;
  };

  const editorDidMount = (editor: any) => {
    console.log("editorDidMount", editor, editor.getValue(), editor.getModel());
  };

  const treeItems: MenuProps['items'] = [
    {
      label: '创建目录',
      key: '1',
    },
    {
      label: '创建文件',
      key: '2',
    },
    {
      label: '重命名',
      key: '3',
    },
  ];
  const generateUniqueId = () => {
    return '_' + Math.random().toString(36).substr(2, 9);
  };


  const handleCreateDirectory = (parentNode: DataNode) => {
    const newDirectoryId = generateUniqueId();
    const newDirectory = {
      key: newDirectoryId,
      title: `新目录 ${newDirectoryId}`,
      children: [],
    };

    // 将新目录添加到当前选中节点的 children 属性中
    parentNode.children = [...(parentNode.children || []), newDirectory];
    // 强制重新渲染树形结构
    setTreeData([...treeData]);

    updateCatalog(props?.initialValues?.id, JSON.stringify([...treeData]));
  };

  const handleCreateFile = (parentNode: DataNode) => {
    const newFileId = generateUniqueId();
    const newFile = {
      key: newFileId,
      title: `新文件 ${newFileId}`,
      isLeaf: true
    };

    // 将新文件添加到当前选中节点的 children 属性中
    parentNode.children = [...(parentNode.children || []), newFile];
    // 强制重新渲染树形结构
    setTreeData([...treeData]);

    updateCatalog(props?.initialValues?.id, JSON.stringify([...treeData]));
  };

  // Modal中表单的提交处理函数
  const handleModalSubmit = () => {
    // 根据modalTitle生成新的目录或文件对象
    const newItemId = generateUniqueId();
    const newItem = {
      key: newItemId,
      title: modalTitle,
      // 其他属性...
      isLeaf: currentCreateType === 'file',
    };

    if (currentCreateType === 'folder' || currentCreateType === 'file') {
      // 如果有选中的节点，将新的目录或文件添加到当前选中节点的 children 属性中
      if (currentSelectDataNode) {
        currentSelectDataNode.children = [...(currentSelectDataNode.children || []), newItem];
        // 更新树形结构数据，强制重新渲染
        setTreeData([...treeData]);
        updateCatalog(props?.initialValues?.id, JSON.stringify([...treeData]));
      } else {
        // 如果没有选中的节点，将新的目录或文件添加到顶级节点
        setTreeData([...treeData, newItem]);
        updateCatalog(props?.initialValues?.id, JSON.stringify([...treeData, newItem]));
      }
    } else if (currentSelectDataNode) {
      // 如果有选中的节点，更新节点的 title 属性
      currentSelectDataNode.title = modalTitle;
      setTreeData([...treeData]);
      updateCatalog(props?.initialValues?.id, JSON.stringify([...treeData]));
    }
    console.log("newItem", newItem)

    // 隐藏Modal
    setModalOpen(false);
    // 清空modalTitle
    setModalTitle("");
    setCurrentCreateType(undefined);
  };

  //重命名
  const handleRename = (nodeData: DataNode) => {
    setModalTitle(nodeData.title);
    setModalOpen(true);
    setCurrentCreateType(undefined); // 清除当前创建类型
  };

  const handleMenuClick = (e: MenuInfo, nodeData: never) => {
    console.log('click', e);
    e.domEvent.stopPropagation();  // 阻止事件冒泡

    setCurrentSelectDataNode(nodeData)
    setModalOpen(true)
    // 判断点击的是“创建目录”还是“创建文件”
    if (e.key === '1') {
      setCurrentCreateType('folder')
      //handleCreateDirectory(nodeData);
    } else if (e.key === '2') {
      //handleCreateFile(nodeData);
      setCurrentCreateType('file')
    } else if (e.key === '3') {
      handleRename(nodeData);
    }
  };

  const handleAddDirectory = () => {
    const newDirectoryId = generateUniqueId();
    const newDirectory = {
      key: newDirectoryId,
      title: `新目录 ${newDirectoryId}`,
      children: [],
    };

    // 更新树形结构数据，添加新的目录
    setTreeData((prevTreeData) => [...prevTreeData, newDirectory]);
    updateCatalog(props?.initialValues?.id, JSON.stringify([...prevTreeData, newDirectory]));

    console.log(treeData);
  };

  const handleAddFile = () => {
    const newFileId = generateUniqueId();
    const newFile = {
      key: newFileId,
      title: `新文件 ${newFileId}`,
      isLeaf: true
    };

    // 更新树形结构数据，添加新的文件
    setTreeData((prevTreeData) => [...prevTreeData, newFile]);
    updateCatalog(props?.initialValues?.id, JSON.stringify([...prevTreeData, newFile]));
  };

  const genExtra = () => (
    <>
      <Flex wrap="wrap" gap="small">
        <Tooltip title="新建文件">
          <Button size='small' icon={<FileAddOutlined />}  
            onClick={(event) => {
              setModalOpen(true)
              setCurrentCreateType('folder')
              setCurrentSelectDataNode(null)
              event.stopPropagation();
            }}/>
        </Tooltip>
        <Tooltip title="新建目录">
          <Button size='small' icon={<FolderAddOutlined />}  
            onClick={(event) => {
              setModalOpen(true)
              setCurrentCreateType('folder')
              setCurrentSelectDataNode(null)
              event.stopPropagation();
            }}/>
        </Tooltip>
      </Flex>
    </>
  );

  const items: CollapseProps['items'] = [
    {
      key: '1',
      label: '代码目录',
      children: <>
        <div className="collapse-content" style={{ width: '100%' }}>
          <div style={{ width: '100%' }}>
            {/* <div style={{ padding: '5px' }} >
              <Button onClick={() => {
                setModalOpen(true)
                setCurrentCreateType('folder')
                setCurrentSelectDataNode(null)
              }}>新增目录</Button>&nbsp;&nbsp;
              <Button onClick={() => {
                setModalOpen(true)
                setCurrentCreateType('file')
                setCurrentSelectDataNode(null)
              }}>新增文件</Button>
            </div> */}

            <div style={{ height: 'calc(100% - 45px)', overflow: 'auto' }}>
              <DirectoryTree
                defaultExpandAll
                titleRender={(nodeData) => {
                  return (
                    <>
                      <span>{nodeData.title}</span>
                      <span className='right'>
                        <Dropdown menu={{ items: treeItems, onClick: (e) => handleMenuClick(e, nodeData) }} trigger={['click']} placement="bottomRight">
                          <EllipsisOutlined onClick={event => {
                            //setCurrentRightClickDataNode(nodeData)
                            event.stopPropagation()
                          }} />
                        </Dropdown>
                      </span>
                    </>
                  )
                }}
                onClick={(event, nodeData) => {
                  setCode("")
                  YuApi.get(`genCodeTemplate/templateCode/${props?.initialValues?.id}/${nodeData.key}`).then(res => {
                    console.log('onClick', res)
                    setCode(String(res))
                  });
                  setCurrentSelectDataNode(nodeData)
                }}
                defaultExpandedKeys={['0-0-0']}
                treeData={treeData} // 将更新后的树形结构数据传递给 DirectoryTree 组件
              />
            </div>
          </div>
        </div>
      </>,
      extra: genExtra(),
    },
    {
      key: '2',
      label: '参考数据',
      children: <p>11</p>,
    },
    {
      key: '3',
      label: 'This is panel header 3',
      children: <p>22</p>,
    },
  ];

  return (
    <YuForm {...formItemLayout} {...props} >
      <SplitPane split="vertical" defaultSize={400} minSize={300} maxSize={600}>
        <Collapse items={items} ghost size="small" defaultActiveKey={['1']} style={{width: '100%', height: 'calc(100vh - 120px)', overflow: 'auto'}}/>
        
        <div style={{ width: '100%' }}>
          <Button type="link" onClick={async (event) => {
            const record = {
              templateId: props?.initialValues?.id,
              catalogKey: currentSelectDataNode?.key,
              code: code
            };

            console.log(record);
            console.log(code);
            await YuCrud.handleAdd(record, (record) => {return YuApi.add<any>('genCodeTemplate/templateCode', record);});
          }}>保存</Button>
          <div style={{display: 'flex', height: 'calc(100vh - 160px)'}}>
            <div style={{width: '50%', padding: '5px'}}>
            <MonacoEditor
              width='100%'
              theme={theme}
              language={language}
              value={code}
              options={options}
              onChange={onChange}
              editorDidMount={editorDidMount}
            />
            </div>
            <div style={{width: '50%', padding: '5px'}}>
            <MonacoEditor
              width='100%'
              theme={theme}
              language={language}
              value={genCode}
              options={options}
              //onChange={onChange}
              //editorDidMount={editorDidMount}
            />
            </div>
          </div>
          
        </div>
      </SplitPane>
      
      <Modal
        title="录入标题"
        open={modalOpen}
        onOk={handleModalSubmit}
        onCancel={() => setModalOpen(false)}
      >
        <Form>
          <Item label="标题">
            <Input
              value={modalTitle}
              onChange={(e) => setModalTitle(e.target.value)}
            />
          </Item>
        </Form>
      </Modal>
    </YuForm>
  );
};

export default CodeTemplateForm;
