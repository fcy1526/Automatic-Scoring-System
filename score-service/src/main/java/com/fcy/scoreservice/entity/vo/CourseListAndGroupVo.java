package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Course;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 包含小组列表的课程列表视图类
 * @Author: fuchenyang
 * @Date: 2021/3/30 15:44
 */
@Data
public class CourseListAndGroupVo extends Course {
    /**
     * 小组列表
     */
    private List<GroupListVo> groupList;
}
