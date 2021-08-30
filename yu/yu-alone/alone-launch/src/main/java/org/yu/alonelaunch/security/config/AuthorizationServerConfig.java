package org.yu.alonelaunch.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 任务服务配置
 *
 * @author wangxd
 * @date 2021-08-24
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 配置client-id
                .withClient("yu")
                // 配置client-secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问token有效期
                .accessTokenValiditySeconds(3600)
                // 配置申请的权限范围
                .scopes("all")
                // 配置grant_type, 标识授权类型
                .authorizedGrantTypes("authorization_code");
    }
}
