package org.yu.admin.base.security.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.yu.admin.base.util.ExceptionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权异常处理类
 *
 * @author wangxd
 * @date 2021-12-10 22:49
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ExceptionUtil.populateExceptionResponse(response, HttpStatus.FORBIDDEN.value(), accessDeniedException.getLocalizedMessage());
    }
}
