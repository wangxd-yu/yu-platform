import React, { useState } from 'react';
import { Upload, message } from 'antd';
import ProForm, {
  ProFormText,
} from '@ant-design/pro-form';
import { useModel } from 'umi';

import styles from './BaseView.less';
import ImgCrop from 'antd-img-crop';
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import { updateBaseInfo } from '../service';
import './base.less';

// 头像组件 方便以后独立，增加裁剪之类的功能
const AvatarView = ({ avatar, handleChange }: { avatar: string, handleChange: (imageBase64: string) => any }) => {
 
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<string>(avatar);

  const getBase64 = (img: Blob, callback: { (imageUrl: any): void; (arg0: string | ArrayBuffer | null): any; }) => {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
  }
  
  const beforeUpload = (file: { type: string; size: number; }) => {
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

  const uploadButton = (
    <div>
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div style={{ marginTop: 8 }}>点击上传头像</div>
    </div>
  );
  return ( <>
   <ImgCrop rotate shape="round">
      <Upload
        name="avatar"
        listType="picture-card"
        className="avatar-uploader"
        showUploadList={false}
        beforeUpload={beforeUpload}
        onChange={(info: any) => {
          if (info.file.status === 'uploading') {
            setLoading(true)
            return;
          }
          if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, paramImageUrl => {
              setImageUrl(paramImageUrl)
              handleChange(paramImageUrl)
              setLoading(false)
            });
          }
        }}
      >
        {imageUrl ? <img src={imageUrl} className="avatar-uploader" alt="avatar" /> : uploadButton}
      </Upload>
    </ImgCrop>
  </>
)};

const BaseView: React.FC = () => {
  const  { initialState, setInitialState } = useModel('@@initialState');
  const { currentUser } = initialState || {};
  const [portraitBase64, setPortraitBase64] = useState<string>('');
  const [loading, setLoading] = useState(false);


  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();

    if (userInfo) {
      await setInitialState((s) => ({ ...s, currentUser: userInfo }));
    }
  };

  return (
    <div className={styles.baseView}>
      {loading ? null : (
        <>
          <div className={styles.left}>
            <ProForm
              layout="vertical"
              onFinish={async (value) => {
                await updateBaseInfo({...value, username: currentUser?.username, portraitBase64})
                message.success('更新基本信息成功')
                fetchUserInfo()
              }}
              submitter={{
                resetButtonProps: {
                  style: {
                    display: 'none',
                  },
                },
                submitButtonProps: {
                  children: '更新基本信息',
                },
              }}
              initialValues={{
                ...currentUser,
                //phone: currentUser?.phone.split('-'),
              }}
              hideRequiredMark
            >
              <ProFormText
                width="md"
                name="email"
                label="邮箱"
                rules={[
                 {
                    type: 'email',
                    message: '请输入正确的邮箱地址!',
                  }
                ]}
              />
              <ProFormText
                width="md"
                name="name"
                label="昵称"
                rules={[
                  {
                    required: true,
                    message: '请输入您的昵称!',
                  },
                ]}
              />
              {/* <ProFormTextArea
                name="profile"
                label="个人简介"
                rules={[
                  {
                    required: true,
                    message: '请输入个人简介!',
                  },
                ]}
                placeholder="个人简介"
              />
              <ProFormSelect
                width="sm"
                name="country"
                label="国家/地区"
                rules={[
                  {
                    required: true,
                    message: '请输入您的国家或地区!',
                  },
                ]}
                options={[
                  {
                    label: '中国',
                    value: 'China',
                  },
                ]}
              />

              <ProForm.Group title="所在省市" size={8}>
                <ProFormSelect
                  rules={[
                    {
                      required: true,
                      message: '请输入您的所在省!',
                    },
                  ]}
                  width="sm"
                  fieldProps={{
                    labelInValue: true,
                  }}
                  name="province"
                  className={styles.item}
                  request={async () => {
                    return queryProvince().then(({ data }) => {
                      return data.map((item) => {
                        return {
                          label: item.name,
                          value: item.id,
                        };
                      });
                    });
                  }}
                />
                <ProFormDependency name={['province']}>
                  {({ province }) => {
                    return (
                      <ProFormSelect
                        params={{
                          key: province?.value,
                        }}
                        name="city"
                        width="sm"
                        rules={[
                          {
                            required: true,
                            message: '请输入您的所在城市!',
                          },
                        ]}
                        disabled={!province}
                        className={styles.item}
                        request={async () => {
                          if (!province?.key) {
                            return [];
                          }
                          return queryCity(province.key || '').then(({ data }) => {
                            return data.map((item) => {
                              return {
                                label: item.name,
                                value: item.id,
                              };
                            });
                          });
                        }}
                      />
                    );
                  }}
                </ProFormDependency>
              </ProForm.Group>
              <ProFormText
                width="md"
                name="address"
                label="街道地址"
                rules={[
                  {
                    required: true,
                    message: '请输入您的街道地址!',
                  },
                ]}
              />
              <ProFormFieldSet
                name="phone"
                label="联系电话"
                rules={[
                  {
                    required: true,
                    message: '请输入您的联系电话!',
                  },
                  { validator: validatorPhone },
                ]}
              >
                <Input className={styles.area_code} />
                <Input className={styles.phone_number} />
              </ProFormFieldSet> */}
            </ProForm>
          </div>
          <div className={styles.right}>
            <AvatarView avatar={currentUser?.portraitUrl? `/${BASE_URL_PREFIX}/` + currentUser?.portraitUrl : ''} handleChange={(imageBase64: string) => {
              setPortraitBase64(imageBase64.replace(/^data:image\/\w+;base64,/, ""))
            }}/>
          </div>
        </>
      )}
    </div>
  );
};

export default BaseView;
