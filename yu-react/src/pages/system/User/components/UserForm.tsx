import React, { useEffect, useState } from 'react'
import ProForm, { ProFormSelect, ProFormSwitch, ProFormText } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';
import { yuUrlSystem } from '@/utils/yuUrl';
import { Transfer, TreeSelect } from 'antd';
import * as YuCrud from '@/utils/yuCrud';
import * as YuApi from '@/utils/yuApi';
import type { DataNode } from 'rc-tree/lib/interface';
import ImgCrop from 'antd-img-crop';
import { Upload, message } from 'antd';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import './UserForm.less';
import Field from '@ant-design/pro-form/lib/components/Field';
import type { UserData } from '../data';
import { addUser, updateUser } from '../service';
import type { RoleData } from '../../Role/data';

type UserFromProps = YuFormProps & { deptTree: DataNode[] | undefined }

function getBase64(img: Blob, callback: { (imageUrl: any): void; (arg0: string | ArrayBuffer | null): any; }) {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result));
  reader.readAsDataURL(img);
}

function beforeUpload(file: { type: string; size: number; }) {
  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  if (!isJpgOrPng) {
    message.error('You can only upload JPG/PNG file!');
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('Image must smaller than 2MB!');
  }
  return isJpgOrPng && isLt2M;
}

type RoleTransferData = RoleData & { disabled: boolean }

const UserForm: React.FC<YuFormProps & UserFromProps> = (props: UserFromProps) => {
  const { deptTree, onFinish, ...rest } = props
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<any>(props.initialValues?.portraitUrl ? `/${BASE_URL_PREFIX}/` + props.initialValues?.portraitUrl : null);
  const [roleList, setRoleList] = useState<RoleTransferData[]>([]);
  const [roleTargetKeys, setRoleTargetKeys] = useState<string[]>();

  useEffect(() => {
    console.log(props?.initialValues?.roleIds)
    setRoleTargetKeys(props?.initialValues?.roleIds)
    YuApi.queryList<RoleTransferData>(yuUrlSystem('/role')).then(res => {
      return setRoleList(res?.data);
    });
  }, []);

  const uploadButton = (
    <div>
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div style={{ marginTop: 8 }}>点击上传头像</div>
    </div>
  );


  const handleChange = (info: any) => {
    if (info.file.status === 'uploading') {
      setLoading(true)
      return;
    }
    if (info.file.status === 'done') {
      // Get this url from response in real world.
      getBase64(info.file.originFileObj, paramImageUrl => {
        setImageUrl(paramImageUrl)
        props.formRef.current?.setFieldsValue({
          ...props.formRef.current.getFieldsValue(),
          portraitBase64: paramImageUrl.replace(/^data:image\/\w+;base64,/, "") //去掉base64位头部;
        });
        setLoading(false)
      });
    }
  };

  /**
    * 角色绑定 穿梭框 选项在两栏之间转移时的回调函数
    * @param nextTargetKeys 结果 
    */
  const handleRoleChange = (nextTargetKeys: string[]) => {
    setRoleTargetKeys(nextTargetKeys);
  };
  return (
    <YuForm
      formType={'DrawerForm'}
      onFinish={
        async (value) => {
          const data = { ...props.initialValues, ...value };
          let success;
          if (!data.id) {
            success = await YuCrud.handleAdd(data as UserData, addUser);
          } else {
            success = await YuCrud.handleUpdate(data as UserData, updateUser);
          }
          if (success && onFinish) {
            onFinish(value);
          }
        }
      }
      {...rest}>
      <Field name="portraitBase64" >
        <ImgCrop rotate shape="round">
          <Upload
            name="avatar"
            listType="picture-card"
            className="avatar-uploader"
            showUploadList={false}
            beforeUpload={beforeUpload}
            onChange={handleChange}
          >
            {imageUrl ? <img src={imageUrl} className="avatar-uploader" alt="avatar" /> : uploadButton}
          </Upload>
        </ImgCrop>
      </Field>
      <ProFormSwitch name="enabled" label="状态" />
      <ProFormText
        rules={[
          {
            required: true,
            message: '账号称为必填项',
          },
        ]}
        label="账号"
        name="username"
      />
      <ProFormText
        rules={[
          {
            required: true,
            message: '昵称为必填项',
          },
        ]}
        label="昵称"
        name="name"
      />
      <ProForm.Item label="部门" name="deptId" rules={[
        {
          required: true,
          message: '部门为必选项',
        },
      ]}>
        <TreeSelect
          style={{ width: '100%' }}
          listHeight={300}
          dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
          treeData={deptTree}
          placeholder="请选择"
          treeDefaultExpandAll
        />
      </ProForm.Item>
      {/* <ProFormSelect
        fieldProps={{
          mode: 'multiple',
        }}
        name="roleIds"
        label="角色"
        request={async () => {
          const res = await YuApi.queryList<{ name: string; id: string; }>(yuUrlSystem("/role"));
          return res.data.map((item) => {
            return {
              label: item.name,
              value: item.id
            };
          });
        }}
        rules={[{ required: true, message: '角色为必选项!' }]}
      /> */}
      <ProForm.Item label="角色" name="roleIds">
        <Transfer<RoleData>
          dataSource={roleList}
          rowKey={item => item.id}
          titles={['全部角色', '当前角色']}
          render={item => item.name}
          targetKeys={roleTargetKeys}
          onChange={handleRoleChange}
          oneWay
          listStyle={{
            width: 300,
            height: 300,
          }}
        />
      </ProForm.Item>
    </YuForm >
  )
}
export default UserForm;