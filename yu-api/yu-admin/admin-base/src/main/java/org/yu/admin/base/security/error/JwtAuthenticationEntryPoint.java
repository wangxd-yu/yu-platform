package org.yu.admin.base.security.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.yu.admin.base.util.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理类
 *
 * @author wangxd
 * @date 2021-12-10 22:20
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        /**
         * 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
         */
        ExceptionUtil.populateExceptionResponse(response, HttpStatus.UNAUTHORIZED.value(), authException.getLocalizedMessage());
    }
}
