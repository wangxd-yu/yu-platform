import ProCard from '@ant-design/pro-card';
import { PageContainer } from '@ant-design/pro-layout';
import ProTable, { ActionType, ProTableProps } from '@ant-design/pro-table';
import { DataNode } from 'antd/lib/tree';
import React, { useEffect, useRef, useState } from 'react';
import DeptTree from '../DeptTree';
import { getTableScroll } from '@/utils/baseUtil';
import type { ParamsType } from '@ant-design/pro-provider';

export type YuPageContainerProps<DataType extends Record<string, any> = Record<string, any>, Params extends ParamsType = ParamsType, ValueType = "text"> = ProTableProps<DataType, Params, ValueType> & {
    // your additional type definition here
    deptTree: boolean
}

// 设置默认值
const defaults: YuPageContainerProps = {
    deptTree: true
}

// 这里使用React.PropsWithChildren接口来定义函数参数的类型
function YuPageContainer<ProviderWarp extends Record<string, any>>(props: YuPageContainerProps = { ...defaults }) {
    const [deptId, setDeptId] = useState<string>();
    const [deptTree, setDeptTree] = useState<DataNode[]>();

    const actionRef = useRef<ActionType>();
    const [scrollY, setScrollY] = useState("")
    //页面加载完成后才能获取到对应的元素及其位置
    useEffect(() => {
        setScrollY(getTableScroll())
    }, [])
    return (
        <PageContainer header={{
            breadcrumb: {},
        }}>

            <ProCard split="vertical" ghost >
                {props.deptTree &&
                    <ProCard style={{ height: 'calc(100vh - 168px)', background: "white" }} colSpan={{ xs: 12, sm: 12, md: 10, lg: 8, xl: 5 }}>
                        <DeptTree
                            onInit={(deptTree) => { setDeptTree(deptTree) }}
                            onSelect={(deptId) => {
                                setDeptId(deptId);
                                // 引用组件处如果定义了
                                if (props.actionRef) {
                                    (props.actionRef as React.MutableRefObject<ActionType>).current?.reload();
                                } else {
                                    actionRef.current?.reload();
                                }
                            }} />
                    </ProCard>
                }
                <ProCard ghost>
                    <ProTable
                        rowKey="id"
                        search={{
                            labelWidth: 120,
                            onCollapse: () => {
                                setScrollY(getTableScroll())
                            }
                        }}
                        actionRef={actionRef}
                        params={{ deptId, ...props.params }}
                        scroll={{ y: scrollY, x: '1000px' }}
                        {...props} />
                </ProCard>
            </ProCard>
        </PageContainer>
    );
}

export default YuPageContainer;