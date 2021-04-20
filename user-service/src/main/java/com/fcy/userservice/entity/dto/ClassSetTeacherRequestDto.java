package com.fcy.userservice.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @Describe: 分配教师dto
 * @Author: fuchenyang
 * @Date: 2021/3/23 21:26
 */
@Data
public class ClassSetTeacherRequestDto {

    /**
     * 班级id
     */
    private Integer classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 教师id列表
     */
    private List<String> teacherIdList;
}
