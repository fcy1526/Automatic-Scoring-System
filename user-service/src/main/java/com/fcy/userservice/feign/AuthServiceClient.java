package com.fcy.userservice.feign;


import com.fcy.userservice.entity.JWT;
import com.fcy.userservice.feign.hystrix.AuthServiceHystrix;
import com.fcy.userservice.util.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Describe: 获取token
 * @Author: fuchenyang
 * @Date: 2021/3/1 20:41
 */
@FeignClient(name = ServiceName.UAA_SERVICE, fallback = AuthServiceHystrix.class)
public interface AuthServiceClient {

    @PostMapping("/oauth/token")
    JWT getToken(@RequestHeader("Authorization") String authorization, @RequestParam("grant_type") String type,
                 @RequestParam("username") String userName, @RequestParam("password") String password);
}
