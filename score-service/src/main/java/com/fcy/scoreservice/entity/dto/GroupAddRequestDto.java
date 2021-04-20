package com.fcy.scoreservice.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @Describe: 添加小组dto
 * @Author: fuchenyang
 * @Date: 2021/3/30 16:35
 */
@Data
public class GroupAddRequestDto {
    /**
     * 小组id，mybatis回填
     */
    private Integer groupId;
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 组长id
     */
    private String leaderId;
    /**
     * 小组成员id
     */
    private List<String> members;
    /**
     * 班级id
     */
    private Integer classId;
}
