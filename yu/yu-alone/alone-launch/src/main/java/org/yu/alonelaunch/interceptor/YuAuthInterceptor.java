package org.yu.alonelaunch.interceptor;

/**
 * YU 请求拦截器，用于处理 YuContextHolder填充、端点权限控制
 *
 * @author wangxd
 * @date 2021-09-05
 */
/*@Slf4j
public class YuAuthInterceptor implements HandlerInterceptor {

    @Resource
    private EndpointService endpointService;

    @Resource
    private LogEndpointService logEndpointService;

    @Resource
    private MultiTenantProperties multiTenantProperties;

    *//**
     * 权限路径 map
     * 存放 不同tenant的 地址权限 对应关系
     * 不同租户的 端点 访问权限控制 数据结构
     *//*
    private static Map<String, Map<String, Set<String>>> tenantAccessPathMap;
    *//**
     * 日志路径 map
     *//*
    private static Map<String, Set<String>> tenantLogPathMap;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        //需要记录日志
        if (tenantLogPathMap.get(YuContextHolder.getTenantId()).contains(getPath(request))) {
            ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
            ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;
            LogEndpointDO logEndpointDO = LogEndpointDO.builder()
                    .username(YuContextHolder.getYuContext().getClientUser().getUsername())
                    .userId(YuContextHolder.getYuContext().getClientUser().getId())
                    .ip(ServletUtil.getClientIP(requestWrapper))
                    .method(requestWrapper.getMethod())
                    .handler(requestWrapper.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE).toString())
                    .pattern((String) requestWrapper.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE))
                    .url(requestWrapper.getRequestURL().toString())
                    .request(new String(requestWrapper.getContentAsByteArray()))
                    .response(new String(responseWrapper.getContentAsByteArray()))
                    .httpStatus(responseWrapper.getStatus())
                    .build();
            logEndpointService.asyncSave(logEndpointDO);
        }
        YuContextHolder.clearContext();
    }

    @PostConstruct
    public void init() {
        tenantAccessPathMap = new HashMap<>(multiTenantProperties.getTenants().size());
        tenantLogPathMap = new HashMap<>(multiTenantProperties.getTenants().size());
        multiTenantProperties.getTenants().forEach((tenantId, value) -> {
            YuContext yuContext = new YuContext();
            new YuContextHolder(yuContext);
            YuContextHolder.getYuContext().setTenantId(tenantId);
            tenantAccessPathMap.put(tenantId, endpointService.getAccessEndpoints());
            tenantLogPathMap.put(tenantId, endpointService.getLogEndpoints());
            YuContextHolder.clearContext();
        });
    }

    @EventListener(classes = EndpointAccessEvent.class)
    public void accessListener() {
        // 此处如果改为异步执行， tenantId 需要传入，不能直接从 YuContextHolder中获取(使用阿里 TtlExecutors 解决)
        tenantAccessPathMap.put(YuContextHolder.getTenantId(), endpointService.getAccessEndpoints());
    }

    @EventListener(classes = EndpointLogEvent.class)
    public void logListener() {
        // 此处如果改为异步执行， tenantId 需要传入，不能直接从 YuContextHolder中获取(使用阿里 TtlExecutors 解决)
        tenantLogPathMap.put(YuContextHolder.getTenantId(), endpointService.getLogEndpoints());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        new YuContextHolder(populateYuContext());
        if (YuContextHolder.getYuContext() != null) {
            // 不是超级管理员（超级管理员不走 权限校验）
            if (YuContextHolder.getYuContext().getClientUser() != null
                    && !multiTenantProperties.getTenants().get(YuContextHolder.getTenantId()).getAdmins().contains(YuContextHolder.getYuContext().getClientUser().getUsername())) {
                checkPathPermission(request);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    private YuContext populateYuContext() {
        YuContext yuContext = new YuContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal != null && principal instanceof SecurityUser) {
            SecurityUser securityUser = (SecurityUser) principal;
            LoginUser loginUser = new LoginUser();
            loginUser.setId(securityUser.getId());
            loginUser.setClientId(securityUser.getClientId());
            loginUser.setTenantId(securityUser.getTenantId());
            loginUser.setUsername(securityUser.getUsername());
            loginUser.setDeptNo(securityUser.getDeptNo());
            loginUser.setRoles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));

            yuContext.setClientUser(loginUser);
        } else {
            yuContext.setTenantId(TenantUtil.getTenantId());
        }

        return yuContext;
    }

    private void checkPathPermission(HttpServletRequest httpServletRequest) {
        String path = getPath(httpServletRequest);
        Map<String, Set<String>> pathRolesMap = tenantAccessPathMap.get(YuContextHolder.getTenantId());
        Set<String> roles = pathRolesMap == null ? null : pathRolesMap.get(path);
        if (roles != null
                && !CollectionUtil.containsAny(YuContextHolder.getYuContext().getClientUser().getRoles(), pathRolesMap.get(path))) {
            throw new AccessDeniedException(MessageConstant.PERMISSION_DENIED);
        }
    }

    private String getPath(HttpServletRequest httpServletRequest) {
        String method = httpServletRequest.getMethod();
        String bestMatchingPattern = (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return bestMatchingPattern + "[" + method + "]";
    }
}*/
