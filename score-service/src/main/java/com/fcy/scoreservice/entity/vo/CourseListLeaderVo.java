package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Course;
import lombok.Data;

/**
 * @Describe: 小组长评分课程视图类
 * @Author: fuchenyang
 * @Date: 2021/4/1 13:47
 */
@Data
public class CourseListLeaderVo extends Course {
    /**
     * 小组id
     */
    private Integer groupId;
    /**
     * 总人数
     */
    private Integer totalCount;
    /**
     * 目前已评分人数
     */
    private Integer scoreCount;
}
