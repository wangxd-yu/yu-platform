package org.yu.alonelaunch.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.yu.alonelaunch.security.token.jwt.YuJwtAccessTokenConverter;

/**
 * JwtToken配置类
 *
 * @author wangxd
 * @date 2021-08-31
 */
@Configuration
public class JwtTokenStoreConfig {

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        YuJwtAccessTokenConverter yuJwtAccessTokenConverter = new YuJwtAccessTokenConverter();
        yuJwtAccessTokenConverter.setSigningKey("yu");
        return yuJwtAccessTokenConverter;
    }

    @Bean(name = "jwtTokenStore")
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
}
