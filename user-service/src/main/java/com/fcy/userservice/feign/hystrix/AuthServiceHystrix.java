package com.fcy.userservice.feign.hystrix;


import com.fcy.userservice.entity.JWT;
import com.fcy.userservice.feign.AuthServiceClient;
import org.springframework.stereotype.Component;

/**
 * @Describe: 权限请求熔断类
 * @Author: fuchenyang
 * @Date: 2021/3/11 20:37
 */
@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String userName, String password) {
        return null;
    }
}
