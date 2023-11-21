import { ProFormText } from "@ant-design/pro-components";

interface ModuleConfigTabProps {
    onValuesChange: (values: any) => void;
}

const ModuleConfigTab: React.FC<ModuleConfigTabProps> = (prop) => {

    return <>
        <ProFormText
            rules={[
                {
                    required: true,
                    message: '模块名称为必填项',
                },
            ]}
            label="模块名称"
            name="moduleName"
        />
        <ProFormText
            rules={[
                {
                    required: true,
                    message: '模块标题为必填项',
                },
            ]}
            label="模块标题"
            name="moduleTitle"
        />
        <ProFormText
            rules={[
                {
                    required: true,
                    message: '作者为必填项',
                },
            ]}
            label="作者"
            name="author"
        />
        <ProFormText
            rules={[
                {
                    required: true,
                    message: '作者为必填项',
                },
            ]}
            label="基础包名"
            name="packagePath"
        />
    </>
}

export default ModuleConfigTab