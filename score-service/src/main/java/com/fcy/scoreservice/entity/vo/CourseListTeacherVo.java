package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Course;
import lombok.Data;

/**
 * @Describe: 最终项目评分课程列表视图类
 * @Author: fuchenyang
 * @Date: 2021/4/1 16:21
 */
@Data
public class CourseListTeacherVo extends Course {
    /**
     * 班级总人数
     */
    private Integer totalCount;
    /**
     * 评分人数
     */
    private Integer scoreCount;
}
