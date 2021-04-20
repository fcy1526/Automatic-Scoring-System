package com.fcy.scoreservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 当前评分班级信息Dto
 * @Author: fuchenyang
 * @Date: 2021/3/31 15:54
 */
@Data
public class ScoreClassInfoDto {

    /**
     * 班级id
     */
    private Integer classId;
    /**
     * 课程id
     */
    private Integer courseId;
}
