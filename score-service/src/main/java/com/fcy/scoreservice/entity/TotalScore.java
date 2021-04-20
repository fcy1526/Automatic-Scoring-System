package com.fcy.scoreservice.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Describe: 总分实体类
 * @Author: fuchenyang
 * @Date: 2021/4/19 20:18
 */
@Data
@Builder
public class TotalScore {
    /**
     * 学生id
     */
    private String studentId;
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 学生表现评分
     */
    private double studentScore;
    /**
     * 小组评分
     */
    private double groupScore;
    /**
     * 小组互评
     */
    private double mutualScore;
    /**
     * 小组长评分
     */
    private Integer leaderScore;
    /**
     * 最终项目评分
     */
    private Integer teacherScore;
    /**
     * 总评分
     */
    private double score;
}
