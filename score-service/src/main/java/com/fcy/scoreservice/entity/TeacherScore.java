package com.fcy.scoreservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 教师评分实体类
 * @Author: fuchenyang
 * @Date: 2021/3/30 11:03
 */
@Data
public class TeacherScore implements Serializable {
    private static final long serialVersionUID = 8085900158336653661L;
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
