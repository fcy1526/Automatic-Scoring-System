package com.fcy.scoreservice.entity.dto;

import com.fcy.scoreservice.entity.StudentScore;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 学生表现成绩Dto
 * @Author: fuchenyang
 * @Date: 2021/3/31 18:35
 */
@Data
public class StudentScoreDto {

    /**
     * 当前阶段
     */
    private Integer currentStage;
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 成绩列表
     */
    private List<StudentScore> scoreList;
}
