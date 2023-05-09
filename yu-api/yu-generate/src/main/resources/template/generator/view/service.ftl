import type { LogLoginData } from './data.ftl';
import * as YuApi from '@/utils/yuApi';
import { yuUrlSystem } from '@/utils/yuUrl';
import type { SortOrder } from 'antd/lib/table/interface';

const logLoginUrl = yuUrlSystem('/logLogin');

/** 获取规则列表 GET */
export async function queryLogLogin(
params?: any,
sort?: Record
<string, SortOrder>
) {
return YuApi.queryPage
<LogLoginData>(logLoginUrl, params, sort);
    }