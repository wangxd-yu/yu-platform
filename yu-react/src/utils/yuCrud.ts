import { message } from 'antd';

/**
 * 新增
 * @param fields 
 * @param addHandler 
 * @returns 
 */
 export const handle = async <T> (fields: T, handler: (record: T) => Promise<any>, title: string = '处理')  => {
  const hide = message.loading(`正在${title}`);

  try {
    await handler({ ...fields });
    hide();
    message.success(`${title}成功`);
    return true;
  } catch (error) {
    hide();
    message.error(`${title}失败请重试！`);
    return false;
  }
}

/**
 * 新增
 * @param fields 
 * @param addHandler 
 * @returns 
 */
export const handleAdd = async <T> (fields: T, addHandler: (record: T) => Promise<any>)  => {
  const hide = message.loading('正在添加');

  try {
    await addHandler({ ...fields });
    hide();
    message.success('添加成功');
    return true;
  } catch (error) {
    hide();
    message.error('添加失败请重试！');
    return false;
  }
}

/**
 * 更新
 */
 export const handleUpdate = async <T> (fields: T, updateHandler: (record: T) => Promise<any>) => {
  const hide = message.loading('正在更新');

  try {
    await updateHandler({ ...fields });
    hide();
    message.success('更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('更新失败请重试！');
    return false;
  }
};

/**
 * 删除
 */

export const handleDelete = async (deleteHandler: (...fields: (string | number)[]) => Promise<any>, ...fields: string[] | number[]) => {
  const hide = message.loading('正在删除');
  if (!fields) return true;

  try {
    await deleteHandler(...fields);
    hide();
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};

