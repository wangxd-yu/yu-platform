package org.yu.admin.base.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.yu.admin.base.security.error.JwtAccessDeniedHandler;
import org.yu.admin.base.security.error.JwtAuthenticationEntryPoint;
import org.yu.admin.base.security.filter.JwtTokenAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring Security 配置
 *
 * @author wangxd
 * @date 2021-12-10 14:47
 */
@Configuration
public class SecurityConfig {

    @Resource
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .antMatcher("/**")
                // 禁用 CSRF
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler).and()
                // 不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/auth/login").anonymous()
                        .antMatchers("/upload/**").anonymous()
                        // 所有请求都需要认证
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
