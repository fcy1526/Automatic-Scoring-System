package com.fcy.scoreservice.feign.hystrix;

import com.fcy.scoreservice.feign.UserServiceClient;
import com.fcy.scoreservice.util.dto.Result;
import org.springframework.stereotype.Component;

/**
 * @Describe: 用户通用服务熔断类
 * @Author: fuchenyang
 * @Date: 2021/3/31 14:56
 */
@Component
public class UserServiceHystrix implements UserServiceClient {
    @Override
    public Result<Object> getTotalCount(Integer classId) {
        return null;
    }
}
