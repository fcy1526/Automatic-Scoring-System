package com.fcy.userservice.entity.vo;


import com.fcy.userservice.enums.SexEnum;
import lombok.Data;

/**
 * @Describe: 用户个人信息返回类
 * @Author: fuchenyang
 * @Date: 2021/3/11 20:39
 */
@Data
public class UserInfoVo {
    // 学号
    private String userId;
    // 真实姓名
    private String trueName;
    // 性别
    private SexEnum sex;
}
