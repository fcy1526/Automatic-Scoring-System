package com.fcy.userservice.entity.vo;

import com.fcy.userservice.entity.Class;
import com.fcy.userservice.entity.User;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Describe: 班级列表视图类
 * @Author: fuchenyang
 * @Date: 2021/3/23 18:13
 */
@Data
public class ClassListVo extends Class {

    /**
     * 班级人数
     */
    private Integer count;

    /**
     * 教师列表
     */
    private List<User> teachers;

    /**
     * 教师id列表
     */
    private List<String> teacherIdList;

    /**
     * 学生列表
     */
    private List<ClassStudentVo> studentList;

    /**
     * 提取教师id
     */
    public void setTeacherId() {
        teacherIdList = teachers.stream().map(User::getUserId).collect(Collectors.toList());
    }
}
