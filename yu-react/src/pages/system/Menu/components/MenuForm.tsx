import React, { useState } from 'react'
import { TreeSelect } from 'antd'
import ProForm, { DrawerForm, ProFormDigit, ProFormRadio, ProFormSwitch, ProFormText } from '@ant-design/pro-form'
import type { DataNode } from 'rc-tree-select/lib/interface';
import { menuTypeArr, MenuTypeEnum } from '../index';
import type { MenuData } from '../data';
import type { YuFormProps } from '@/components/Yu/YuForm';

export type MenuFormProps = YuFormProps & {
    menuDataList: MenuData[];
}

const formItemLayout = {
    labelCol: { span: 5 },
    wrapperCol: { span: 19 },
};

const MenuForm: React.FC<MenuFormProps> = (props: MenuFormProps) => {
    const [menuType, setMenuType] = useState<MenuTypeEnum>(MenuTypeEnum.FOLDER);

    const disableTreeNode = (nodeMenuType: MenuTypeEnum): boolean => {
        switch (menuType) {
            case MenuTypeEnum.FOLDER:
                return false;
            case MenuTypeEnum.MENU:
                return false;
            case MenuTypeEnum.PERMISSION:
                return nodeMenuType !== MenuTypeEnum.MENU;
            default:
                return false;
        }
    }

    const handleTreeDataRecursion = (data: MenuData[]): DataNode[] => {
        const item: DataNode[] = [];
        if (Array.isArray(data)) {
            data?.forEach((list: MenuData) => {
                const newData: DataNode = {} as DataNode;
                newData.key = list.id;
                newData.value = list.id;
                newData.title = list.name;
                newData.disabled = disableTreeNode(MenuTypeEnum[list.type]);
                newData.children = list.children ? handleTreeDataRecursion(list.children) : []; // 如果还有子集，就再次调用自己
                if (menuType === MenuTypeEnum.FOLDER && MenuTypeEnum[list.type] !== MenuTypeEnum.FOLDER) {
                    return;
                }
                if (menuType !== MenuTypeEnum.FOLDER && menuType === MenuTypeEnum[list.type]) {
                    return;
                }
                item.push(newData);
            });
        }
        return item;
    }

    const handleTreeData = (data: MenuData[]) => {
        return [{
            key: 0,
            value: "0",
            title: "顶级目录",
            disabled: disableTreeNode(MenuTypeEnum.FOLDER),
            children: handleTreeDataRecursion(data)
        }]
    }

    return (
        <DrawerForm
            title={!props.isAdd ? '更新菜单' : '新建菜单'}
            width="600px"
            formRef={props.formRef}
            {...formItemLayout}
            visible={props.visible}
            layout='horizontal'
            onVisibleChange={(visible) => {
                if (props.onVisibleChange) props.onVisibleChange(visible);
                if (visible) {
                    const initialType = MenuTypeEnum[props?.initialValues?.type];
                    setMenuType(initialType || MenuTypeEnum.FOLDER);
                }
            }}
            onFinish={props.onFinish}
            initialValues={props.initialValues}
        >
            <ProFormRadio.Group
                label="菜单类型"
                radioType="button"
                disabled={!props.isAdd}
                fieldProps={{
                    value: menuType,
                    buttonStyle: "solid",
                    onChange: (e) => {
                        setMenuType(e.target.value);
                        props.formRef.current?.setFieldsValue({
                            ...props.formRef.current.getFieldsValue(),
                            pid: undefined
                        });
                    }
                }}
                options={menuTypeArr}
                name="type"
            />
            <ProFormText
                rules={[
                    {
                        required: true,
                        message: '名称为必填项',
                    },
                ]}
                label="名称"
                name="name"
            />
            <ProForm.Item label="上级" name="pid" rules={[
                {
                    required: true,
                    message: '上级为必选项',
                },
            ]}>
                <TreeSelect
                    style={{ width: '100%' }}
                    listHeight={300}
                    dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                    treeData={handleTreeData(props.menuDataList)}
                    placeholder="请选择"
                    treeDefaultExpandAll
                />
            </ProForm.Item>
            <ProFormDigit rules={[
                {
                    required: true,
                    message: '排序为必填项',
                },
            ]} label="排序" name="sort" min={1} max={99} />
            {
                (menuType === MenuTypeEnum.FOLDER || menuType === MenuTypeEnum.MENU) &&
                <>
                    <ProFormText label="图标" name="icon" />
                    <ProFormSwitch name="switch" label="是否隐藏" />
                    <ProFormText
                        rules={[
                            {
                                required: true,
                                message: '路由路径为必填项',
                            },
                        ]}
                        label="路由路径"
                        name="path"
                    />
                </>
            }
            {
                menuType === MenuTypeEnum.MENU &&
                <ProFormText
                    rules={[
                        {
                            required: true,
                            message: '组件路径为必填项',
                        },
                    ]}
                    label="组件路径"
                    name="component"
                />
            }
            {
                menuType === MenuTypeEnum.PERMISSION &&
                <ProFormText
                    rules={[
                        {
                            required: true,
                            message: '权限编码为必填项',
                        },
                    ]}
                    label="权限编码"
                    name="permission"
                />
            }
        </DrawerForm >
    )
}

export default MenuForm;