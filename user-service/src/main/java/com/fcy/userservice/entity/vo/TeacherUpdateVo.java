package com.fcy.userservice.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @Describe: 教师信息视图类
 * @Author: fuchenyang
 * @Date: 2021/3/23 0:26
 */
@Data
public class TeacherUpdateVo extends UserInfoVo {

    /**
     * 教学班级
     */
    private List<Integer> teachClasses;
}
