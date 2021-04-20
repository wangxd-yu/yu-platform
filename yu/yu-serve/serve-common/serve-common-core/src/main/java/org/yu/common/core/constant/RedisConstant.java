package org.yu.common.core.constant;

/**
 * Redis常量
 *
 * @author wangxd
 * @date 2020/6/19
 */
public interface RedisConstant {
    String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    String CACHE_CLIENT_KEY = "oauth_client_details";
}
