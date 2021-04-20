package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Course;
import lombok.Data;

/**
 * @Describe: 小组评分课程列表视图类
 * @Author: fuchenyang
 * @Date: 2021/4/1 19:27
 */
@Data
public class CourseListGroupVo extends Course {
    /**
     * 当前阶段开始时间
     */
    private String currentStageTime;
    /**
     * 当前阶段id
     */
    private Integer currentStageId;
    /**
     * 班级总小组数
     */
    private Integer groupCount;
    /**
     * 目前已评分小组数
     */
    private Integer scoreCount;
}
