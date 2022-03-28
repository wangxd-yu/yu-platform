import { message } from 'antd';

/**
 * 通用处理
 * @param fields
 * @param addHandler
 * @returns
 */
 export const handle = async <T> (fields: T, handler: (record: T) => Promise<any>, title: string = '处理')  => {
  const hide = message.loading(`正在${title}`);

  try {
    hide();
    await handler({ ...fields });
    message.success(`${title}成功`);
    return true;
  } catch (error) {
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
    hide();
    await addHandler({ ...fields });
    message.success('添加成功');
    return true;
  } catch (error) {
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
    hide();
    await updateHandler({ ...fields });
    message.success('更新成功');
    return true;
  } catch (error) {
    message.error('更新失败请重试！');
    return false;
  }
};

/**
 * 删除 根据对象
 */

export const handleDelete = async <T> (deleteHandler: (record: T) => Promise<any>, fields: T) => {
  const hide = message.loading('正在删除');
  if (!fields) return true;

  try {
    hide();
    await deleteHandler(fields);
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    message.error('删除失败，请重试');
    return false;
  }
};

/**
 * 删除-根据 id 列表
 */

 export const handleDeleteByIds = async (batchDeleteHandler: (...ids: (string | number)[]) => Promise<any>, ...ids: string[] | number[]) => {
  const hide = message.loading('正在删除');
  if (!ids) return true;

  try {
    hide();
    await batchDeleteHandler(...ids);
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    message.error('删除失败，请重试');
    return false;
  }
};

/**
 * 删除-根据 id 
 */

 export const handleDeleteById = async (deleteHandler: (id: (string | number)) => Promise<any>, id: string | number) => {
  const hide = message.loading('正在删除');
  if (!id) return true;

  try {
    hide();
    await deleteHandler(id);
    message.success('删除成功，即将刷新');
    return true;
  } catch (error) {
    message.error('删除失败，请重试');
    return false;
  }
};

