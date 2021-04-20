package com.fcy.scoreservice.entity;

import com.fcy.scoreservice.enums.CourseStatus;
import lombok.Data;

/**
 * @Describe: 课程类
 * @Author: fuchenyang
 * @Date: 2021/3/28 20:38
 */
@Data
public class Course {
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 课程状态
     */
    private CourseStatus status;
    /**
     * 课程阶段数
     */
    private Integer stageNum;
    /**
     * 教师id
     */
    private String teacherId;
    /**
     * 当前阶段
     */
    private Integer currentStage;
}
