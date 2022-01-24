import React, { useState } from 'react'
import ProForm, { ProFormSelect, ProFormSwitch, ProFormText } from '@ant-design/pro-form'
import type { YuFormProps } from '@/components/Yu/YuForm';
import YuForm from '@/components/Yu/YuForm';
import { yuUrlSystem } from '@/utils/yuUrl';
import { Col, Row, TreeSelect } from 'antd';
import * as YuApi from '@/utils/yuApi';
import type { DataNode } from 'rc-tree/lib/interface';
import ImgCrop from 'antd-img-crop';
import { Upload, message } from 'antd';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import './UserForm.less';
import Field from '@ant-design/pro-form/lib/components/Field';

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


const UserForm: React.FC<YuFormProps & UserFromProps> = (props: UserFromProps) => {
  const { deptTree, ...rest } = props
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<any>(props.initialValues?.portraitUrl? `/${BASE_URL_PREFIX}/` + props.initialValues?.portraitUrl : null);
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
  return (
    <YuForm {...rest}>
      <Row>
        <Col span={16}>
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
          <ProForm.Item label="部门" name="deptNo" rules={[
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
          <ProFormSelect
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
          />
        </Col>
        <Col span={8} style={{ textAlign: "right" }}>
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
        </Col>
      </Row>
    </YuForm >
  )
}
export default UserForm;