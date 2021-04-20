package com.fcy.userservice.entity.vo;

import lombok.Data;

/**
 * @Describe: 学生信息视图类
 * @Author: fuchenyang
 * @Date: 2021/3/21 14:59
 */
@Data
public class StudentUpdateVo extends UserInfoVo {

    /**
     * 班级id
     */
    private Integer classId;
}
