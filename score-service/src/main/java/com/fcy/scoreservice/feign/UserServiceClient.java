package com.fcy.scoreservice.feign;

import com.fcy.scoreservice.feign.hystrix.UserServiceHystrix;
import com.fcy.scoreservice.util.ServiceName;
import com.fcy.scoreservice.util.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ServiceName.USER_SERVICE ,fallback = UserServiceHystrix.class)
public interface UserServiceClient {

    @GetMapping("class/count")
    Result<Object> getTotalCount(@RequestParam("classId") Integer classId);

    @GetMapping("class/name/{classId}")
    Result<Object> getClassName(@PathVariable("classId") Integer classId);

}
