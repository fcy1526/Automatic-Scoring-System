package com.fcy.scoreservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 评分占比dto
 * @Author: fuchenyang
 * @Date: 2021/4/19 12:44
 */
@Data
public class ProportionDto {
    /**
     * 学生表现评分
     */
    private Integer studentScore;
    /**
     * 小组评分
     */
    private Integer groupScore;
    /**
     * 小组互评
     */
    private Integer mutualScore;
    /**
     * 小组长评分
     */
    private Integer leaderScore;
    /**
     * 最终项目评分
     */
    private Integer teacherScore;

    /**
     * 获得总占比
     * @return
     */
    public Integer getSum() {
        return studentScore + groupScore + mutualScore + leaderScore + teacherScore;
    }
}
