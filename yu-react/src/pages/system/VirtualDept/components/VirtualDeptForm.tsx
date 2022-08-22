import YuForm, { YuFormProps } from "@/components/Yu/YuForm"
import { addVirtualDept, updateVirtualDept } from "@/services/system/virtualDept"
import { ProFormDigit, ProFormText } from "@ant-design/pro-form"
import * as YuCrud from '@/utils/yuCrud';
import { ApartmentOutlined } from "@ant-design/icons";

const VirtualDeptForm: React.FC<YuFormProps & {pDept: API.VirtualDept}> = (props: YuFormProps & {pDept: API.VirtualDept}) => {
    const { pDept, ...yuProps} = props;
    console.log('pDept', pDept)
    return (
        <YuForm
            {...yuProps}
            title="新建虚拟部门"
            width="500px"
            onFinish={async (formData: API.VirtualDept) => {
                const data = { ...props.initialValues, ...formData };
                let success;
                if (!formData.id) {
                    success = await YuCrud.handleAdd(data as API.VirtualDept, addVirtualDept);
                } else {
                    success = await YuCrud.handleUpdate(data as API.VirtualDept, updateVirtualDept);
                }
                if (success && props.onFinish) {
                    props.onFinish(formData);
                }
            }}
        >
            <ProFormText
                rules={[
                    {
                        required: true,
                        message: '名称称为必填项',
                    },
                ]}
                fieldProps={{ maxLength: 20 }}
                label="名称"
                name="name"
            />

            <ProFormText
                label="所属部门"
                readonly
            >
                <ApartmentOutlined /> {props.pDept?.name || props.pDept?.title}
            </ProFormText>

            <ProFormDigit rules={[
                {
                    required: true,
                    message: '排序为必填项',
                },
            ]} label="排序" name="sort" min={1} max={99} />
        </YuForm>
    )
}

export default VirtualDeptForm;