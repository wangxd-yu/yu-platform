package org.yu.admin.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-08-20
 */
@RestController
public class UrlController {

    @Autowired
    WebApplicationContext applicationContext;

    @GetMapping("/getAllUrl")
    public List<String> getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<String> urls = new ArrayList<>();
        for (RequestMappingInfo info : map.keySet()) {
            //获取url的Set集合，一个方法可能对应多个url
            Set<String> patterns = info.getPatternsCondition().getPatterns();

            // 这里可获取请求方式 Get,Post等等
//          Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            for (String url : patterns) {
                urls.add(url);
            }
        }
        return urls;
    }


}
