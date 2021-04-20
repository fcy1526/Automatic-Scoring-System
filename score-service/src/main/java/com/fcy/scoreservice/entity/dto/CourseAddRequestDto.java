package com.fcy.scoreservice.entity.dto;

import com.fcy.scoreservice.enums.CourseStatus;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 添加课程请求dto
 * @Author: fuchenyang
 * @Date: 2021/3/29 16:56
 */
@Data
public class CourseAddRequestDto {
    /**
     * 课程id mybatis回填
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
     * 评分阶段数
     */
    private Integer stageNum;
    /**
     * 实践班级id
     */
    private List<Integer> classIds;
    /**
     * 教师id
     */
    private String teacherId;
    /**
     * 课程状态
     */
    private CourseStatus status;
    /**
     * 当前阶段
     */
    private Integer currentStage;
}
