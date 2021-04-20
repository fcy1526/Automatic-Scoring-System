package com.fcy.scoreservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 学生表现评分
 * @Author: fuchenyang
 * @Date: 2021/3/30 10:54
 */
@Data
public class StudentScore implements Serializable {

    private static final long serialVersionUID = -1282571991496178649L;
    /**
     * id
     */
    private Integer id;
    /**
     * 学生id
     */
    private String userId;
    /**
     * 阶段id
     */
    private Integer stageId;
    /**
     * 成绩
     */
    private Integer score;
    /**
     * 班级id
     */
    private Integer classId;
}
