package com.fcy.userservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 用户登录信息
 * @Author: fuchenyang 
 * @Date: 2021/2/14 20:01
 */
@Data
public class UserLoginRequestDto {
    // 学号
    private String userId;
    // 密码
    private String password;
    // 验证码
    private String captcha;
    // 验证码加密值
    private String imageHashCode;
}
