package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Course;
import lombok.Data;

/**
 * @Describe: 进行中的课程列表视图类
 * @Author: fuchenyang
 * @Date: 2021/3/31 14:30
 */
@Data
public class CourseListProcessVo extends Course {
    /**
     * 当前阶段开始时间
     */
    private String currentStageTime;
    /**
     * 班级总人数
     */
    private Integer classCount;
    /**
     * 目前已评分人数
     */
    private Integer scoreCount;
    /**
     * 当前阶段id
     */
    private Integer currentStageId;
}
