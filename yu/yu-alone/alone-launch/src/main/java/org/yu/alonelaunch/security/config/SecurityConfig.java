package org.yu.alonelaunch.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.yu.alonelaunch.security.filter.YuAuthFilter;

/**
 * Security 配置文件
 *
 * @author wangxd
 * @date 2021-08-24
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final YuAuthFilter yuAuthFilter;

    public SecurityConfig(YuAuthFilter yuAuthFilter) {
        this.yuAuthFilter = yuAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 授权认证
        http.authorizeRequests()
                .antMatchers("/oauth/**", "/user/**").permitAll()
                // 所有请求必须被认证，登录之后可访问
                .anyRequest().authenticated();

        http.addFilterBefore(yuAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
