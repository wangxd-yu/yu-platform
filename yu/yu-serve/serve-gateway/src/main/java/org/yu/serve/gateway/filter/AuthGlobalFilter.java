package org.yu.serve.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 将登录用户的JWT转化成用户信息的全局过滤器
 *
 * @author wangxd
 * @date 2020/12/12
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StrUtil.isEmpty(token)) {
            //exchange.transformUrl(exchange.getRequest().getURI().getPath()+"?client_secret=123456");
            //ServerHttpRequest request = exchange.getRequest().mutate().path(exchange.getRequest().getURI().getPath()+"?client_secret=123456").build();
            //exchange = exchange.mutate().request(request).build();
            String tenantId = getTenantId(exchange.getRequest());
            log.info("AuthGlobalFilter.filter() tenantId:{}", tenantId);
            ServerHttpRequest request = exchange.getRequest().mutate().header("tenantId", tenantId).build();
            exchange.mutate().request(request).build();
        } else {
            try {
                //从token中解析用户信息并设置到Header中去
                String realToken = token.replace("Bearer ", "");
                JWSObject jwsObject = JWSObject.parse(realToken);
                String userStr = jwsObject.getPayload().toString();
                log.info("AuthGlobalFilter.filter() user:{}", userStr);
                ServerHttpRequest request = exchange.getRequest().mutate().header("user", userStr).build();
                exchange = exchange.mutate().request(request).build();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 获取租户id
     * @return  租户id
     * @param request
     */
    private String getTenantId(ServerHttpRequest request) {
        log.info("AuthGlobalFilter getLocalAddress" + request.getLocalAddress());
        log.info("AuthGlobalFilter getRemoteAddress" + request.getRemoteAddress());
        return request.getQueryParams().get("tenantId").get(0);
    }
}
