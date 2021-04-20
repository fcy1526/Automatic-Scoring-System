package com.fcy.scoreservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Describe: feign配置类,在feign请求前，添加权限验证信息
 * @Author: fuchenyang
 * @Date: 2021/3/31 15:06
 */
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前请求Spring信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取请求体
        HttpServletRequest request = attributes.getRequest();
        // 获取Header、或参数等
        String token = request.getHeader("Authorization");
        // 注入Feign请求体
        requestTemplate.header("Authorization", token);
    }
}
