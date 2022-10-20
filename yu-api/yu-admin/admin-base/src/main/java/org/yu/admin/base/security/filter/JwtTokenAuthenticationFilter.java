package org.yu.admin.base.security.filter;

import cn.hutool.core.exceptions.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yu.admin.base.security.pojo.SecurityUser;
import org.yu.admin.base.security.util.JwtTokenUtil;
import org.yu.admin.base.util.ExceptionUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangxd
 * @date 2021-12-10 15:03
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = JwtTokenUtil.resolveToken(request);
            if (token != null && JwtTokenUtil.validateToken(token)) {
                SecurityUser securityUser = JwtTokenUtil.getAuthentication(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        securityUser, null, securityUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ValidateException e) {
            ExceptionUtil.populateExceptionResponse(response, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
