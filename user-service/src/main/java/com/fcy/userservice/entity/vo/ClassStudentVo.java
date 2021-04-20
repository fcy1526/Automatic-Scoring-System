package com.fcy.userservice.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @Describe: 班级内的学生列表视图类
 * @Author: fuchenyang
 * @Date: 2021/3/24 21:26
 */
@Data
public class ClassStudentVo {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 角色列表 例：学生、组长、学习委员
     */
    private List<String> roles;
}
