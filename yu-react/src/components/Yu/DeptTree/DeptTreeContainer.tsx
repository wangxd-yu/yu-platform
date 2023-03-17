import ProCard from '@ant-design/pro-card';
import { PageContainer } from '@ant-design/pro-layout';
import { DataNode } from 'antd/lib/tree';
import React, { useState } from 'react';
import DeptTree from '.';

// 这里使用React.PropsWithChildren接口来定义函数参数的类型
function DeptTreeContainer({ children }: React.PropsWithChildren<any>) {
    const [deptId, setDeptId] = useState<string>();
    const [deptTree, setDeptTree] = useState<DataNode[]>();
    return (
        <PageContainer header={{
            breadcrumb: {},
        }}>

            <ProCard split="vertical" ghost >
                <ProCard style={{ height: 'calc(100vh - 168px)', background: "white" }} colSpan={{ xs: 12, sm: 12, md: 10, lg: 8, xl: 5 }}>
                    <DeptTree
                        onInit={(deptTree) => { setDeptTree(deptTree) }}
                        onSelect={(deptId) => {
                            setDeptId(deptId);
                            //userActionRef.current?.reload();
                        }} />
                </ProCard>
                <ProCard ghost>
                    {children({deptId, deptTree})}
                </ProCard>
            </ProCard>
        </PageContainer>
    );
}

export default DeptTreeContainer;