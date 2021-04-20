package com.fcy.userservice.entity.vo;

import lombok.Data;

/**
 * @Describe: 班级分配教师视图类
 * @Author: fuchenyang
 * @Date: 2021/3/23 20:06
 */
@Data
public class ClassGetTeacherListVo {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 真实姓名
     */
    private String name;
}
