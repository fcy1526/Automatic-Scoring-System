package com.fcy.userservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 修改密码请求类
 * @Author: fuchenyang
 * @Date: 2021/3/14 1:09
 */
@Data
public class PasswordUpdateRequestDto {

    // 学号
    private String userId;
    // 密码
    private String password;
}
