import ProTable, { ActionType, ProTableProps } from '@ant-design/pro-table';
import type { ParamsType } from '@ant-design/pro-provider';
import ProCard from '@ant-design/pro-card';
import { PageContainer } from '@ant-design/pro-layout';
import { DataNode } from 'antd/lib/tree';
import DeptTree from '../DeptTree';
import { useEffect, useRef, useState } from 'react';
import { getTableScroll } from '@/utils/baseUtil';

// 定义高阶组件的类型
type HOCProps<DataType extends Record<string, any>, Params extends ParamsType = ParamsType, ValueType = "text"> = {
    showDeptTree?: boolean;  //是否显示部门树
    tableProps: ProTableProps<DataType, Params, ValueType>; // ProTable的属性
};

type EnhancedTableProps<DataType extends Record<string, any>, Params extends ParamsType = ParamsType, ValueType = "text"> = HOCProps<DataType, Params, ValueType> & { // 定义组件类型
    // 其他属性
};

function YuProTable<DataType extends Record<string, any>, Params extends ParamsType = ParamsType, ValueType = "text">(
    props: EnhancedTableProps<DataType, Params, ValueType>
) {
    const [deptId, setDeptId] = useState<string>();
    const [deptTree, setDeptTree] = useState<DataNode[]>();

    const actionRef = useRef<ActionType>();
    const [scrollY, setScrollY] = useState("")
    //页面加载完成后才能获取到对应的元素及其位置
    useEffect(() => {
        setScrollY(getTableScroll())
    }, [])

    // 设置默认值
    const showDeptTree = props.showDeptTree === undefined ? true : props.showDeptTree;

    return (
        <PageContainer header={{
            breadcrumb: {},
        }}>

            <ProCard split="vertical" ghost >
                {showDeptTree &&
                    <ProCard style={{ height: 'calc(100vh - 168px)', background: "white" }} colSpan={{ xs: 12, sm: 12, md: 10, lg: 8, xl: 5 }}>
                        <DeptTree
                            onInit={(deptTree) => { setDeptTree(deptTree) }}
                            onSelect={(deptId) => {
                                setDeptId(deptId);
                                // 引用组件处如果定义了
                                if (props.tableProps.actionRef) {
                                    (props.tableProps.actionRef as React.MutableRefObject<ActionType>).current?.reload();
                                } else {
                                    actionRef.current?.reload();
                                }
                            }} />
                    </ProCard>
                }
                <ProCard ghost>
                    <ProTable
                        tableLayout="fixed"
                        rowKey="id"
                        search={{
                            labelWidth: 120,
                            onCollapse: () => {
                                setScrollY(getTableScroll())
                            }
                        }}
                        actionRef={actionRef}
                        params={{ deptId, ...props.tableProps.params } as unknown as Params}
                        scroll={{ y: scrollY, x: '1000px' }}
                        pagination={{
                            showSizeChanger: true,
                        }}
                        {...props.tableProps} />
                </ProCard>
            </ProCard>
        </PageContainer>
    );
}
export default YuProTable;
