package com.fcy.scoreservice.entity.vo;

import lombok.Data;

/**
 * @Describe: 学生课程成绩视图类
 * @Author: fuchenyang
 * @Date: 2021/4/19 22:47
 */
@Data
public class UserScoreVo {
    /**
     * 课程名称
     */
    private String name;
    /**
     * 课程总成绩
     */
    private double score;
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
}
