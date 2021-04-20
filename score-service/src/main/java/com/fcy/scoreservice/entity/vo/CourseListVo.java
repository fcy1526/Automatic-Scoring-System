package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Course;
import com.fcy.scoreservice.entity.Stage;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 课程列表视图类
 * @Author: fuchenyang
 * @Date: 2021/3/29 21:09
 */
@Data
public class CourseListVo extends Course {
    /**
     * 班级列表
     */
    private List<String> classList;
    /**
     * 阶段列表
     */
    private List<Stage> stages;

}
