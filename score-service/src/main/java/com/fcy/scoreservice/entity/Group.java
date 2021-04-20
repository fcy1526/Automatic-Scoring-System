package com.fcy.scoreservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 小组实体类
 * @Author: fuchenyang
 * @Date: 2021/3/30 12:05
 */
@Data
public class Group implements Serializable {
    private static final long serialVersionUID = 922732511981885181L;
    /**
     * 小组id
     */
    private Integer groupId;
    /**
     * 组长id
     */
    private String leaderId;
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 班级id
     */
    private Integer classId;

}
