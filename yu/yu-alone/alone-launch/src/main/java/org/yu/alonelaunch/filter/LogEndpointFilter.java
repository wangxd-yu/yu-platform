package org.yu.alonelaunch.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.yu.alonelaunch.log.domain.LogEndpointDO;
import org.yu.alonelaunch.log.service.LogEndpointService;
import org.yu.common.core.context.YuContextHolder;
import org.yu.serve.system.module.endpoint.service.EndpointService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangxd
 * @date 2021-12-09 14:45
 */
public abstract class LogEndpointFilter extends OncePerRequestFilter {

    protected final EndpointService endpointService;
    protected final LogEndpointService logEndpointService;

    protected LogEndpointFilter(EndpointService endpointService, LogEndpointService logEndpointService) {
        this.endpointService = endpointService;
        this.logEndpointService = logEndpointService;
    }

    public abstract void init();

    public abstract String getEndpointId(String path);

    protected String getPath(HttpServletRequest httpServletRequest) {
        String method = httpServletRequest.getMethod();
        String bestMatchingPattern = (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return bestMatchingPattern == null ? null : bestMatchingPattern + "[" + method + "]";
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            //需要记录日志
            String path = getPath(requestWrapper);
            if (path != null) {
                String endpointId = getEndpointId(path);
                if (endpointId != null) {
                    String requestStr = new String(requestWrapper.getContentAsByteArray());
                    String responseStr = new String(responseWrapper.getContentAsByteArray());
                    LogEndpointDO logEndpointDO = LogEndpointDO.builder()
                            .endpointId(endpointId)
                            .username(YuContextHolder.getYuContext().getClientUser().getUsername())
                            .userId(YuContextHolder.getYuContext().getClientUser().getId())
                            .ip(ServletUtil.getClientIP(requestWrapper))
                            .method(requestWrapper.getMethod())
                            .handler(requestWrapper.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE).toString())
                            .pattern((String) requestWrapper.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE))
                            .url(requestWrapper.getRequestURL().toString())
                            .request(StrUtil.isBlank(requestStr) ? null : requestStr)
                            .response(StrUtil.isBlank(responseStr) ? null : responseStr)
                            .httpStatus(responseWrapper.getStatus())
                            .time((int) (System.currentTimeMillis() - start))
                            .build();
                    logEndpointService.asyncSave(logEndpointDO);
                    YuContextHolder.clearContext();
                }
            }
            // Do not forget this line after reading response content or actual response will be empty!
            responseWrapper.copyBodyToResponse();
        }
    }
}
