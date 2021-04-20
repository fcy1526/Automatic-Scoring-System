package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Course;
import lombok.Data;

/**
 * @Describe: 小组互评课程视图类
 * @Author: fuchenyang
 * @Date: 2021/3/31 22:41
 */
@Data
public class CourseListMutualVo extends Course {
    /**
     * 阶段id
     */
    private Integer stageId;
    /**
     * 当前阶段开始时间
     */
    private String currentStageTime;
    /**
     * 小组人数
     */
    private Integer groupCount;
    /**
     * 目前已评分人数
     */
    private Integer scoreCount;
}
