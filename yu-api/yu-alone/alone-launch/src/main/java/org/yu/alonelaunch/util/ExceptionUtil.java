package org.yu.alonelaunch.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.yu.common.web.exception.handler.ApiError;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author wangxd
 * @date 2021-12-14 11:24
 */
@Component
public class ExceptionUtil {
    @Resource
    private ObjectMapper objectMapper;

    private static ExceptionUtil exceptionUtil;

    @PostConstruct
    void init() {
        exceptionUtil = this;
    }

    public static void populateExceptionResponse(HttpServletResponse response, int state, String message) throws IOException {
        ApiError apiError = new ApiError(state, message);
        response.setStatus(apiError.getStatus());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        response.getWriter().write(exceptionUtil.objectMapper.writeValueAsString(apiError));
        response.getWriter().flush();
    }

}
