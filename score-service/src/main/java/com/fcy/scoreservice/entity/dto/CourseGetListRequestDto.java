package com.fcy.scoreservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 获取课程列表请求Dto
 * @Author: fuchenyang
 * @Date: 2021/3/28 20:51
 */
@Data
public class CourseGetListRequestDto {
    /**
     * 班级id
     */
    private Integer classId;
    /**
     * 课程名称
     */
    private String name;
}
