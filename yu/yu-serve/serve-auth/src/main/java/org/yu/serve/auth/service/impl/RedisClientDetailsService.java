package org.yu.serve.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yu.common.core.constant.RedisConstant;

import javax.sql.DataSource;
import java.util.List;

/**
 * oauth_client_details表处理类
 *
 * @author wangxd
 * @date 2021-03-16 14:45
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "client")
public class RedisClientDetailsService extends JdbcClientDetailsService {
    private RedisTemplate<String, Object> redisTemplate;

    public RedisClientDetailsService(DataSource dataSource, RedisTemplate<String, Object> redisTemplate) {
        super(dataSource);
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(key = "#p0")
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }

    /**
     * 将oauth_client_details全表刷入redis
     */
    public void loadAllClientToCache() {
        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list)) {
            log.error("oauth_client_details表数据为空，请检查");
            return;
        }
        list.parallelStream().forEach(client -> redisTemplate.opsForValue().set(clientRedisKey(client.getClientId()), client));
    }

    private String clientRedisKey(String clientId) {
        return RedisConstant.CACHE_CLIENT_KEY + ":" + clientId;
    }
}