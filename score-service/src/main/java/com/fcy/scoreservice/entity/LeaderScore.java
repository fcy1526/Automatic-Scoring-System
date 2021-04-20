package com.fcy.scoreservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 小组长评分实体类
 * @Author: fuchenyang
 * @Date: 2021/3/30 11:01
 */
@Data
public class LeaderScore implements Serializable {
    private static final long serialVersionUID = 2101109483125678199L;
    /**
     * id
     */
    private Integer id;
    /**
     * 学生id
     */
    private String studentId;
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 成绩
     */
    private Integer score;

}
