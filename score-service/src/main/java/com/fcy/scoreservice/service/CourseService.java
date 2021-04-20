package com.fcy.scoreservice.service;

import com.fcy.scoreservice.entity.dto.CourseAddRequestDto;
import com.fcy.scoreservice.entity.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Describe: 
 * @Author: fuchenyang 
 * @Date: 2021/3/28 20:53
 */
public interface CourseService {
    /**
     * 根据条件获取课程列表
     * @param map
     * @return
     */
    PageInfo<CourseListVo> getCourseList(Map map);

    /**
     * 根据条件获取课程列表
     * @param map
     * @return
     */
    PageInfo<CourseListProcessVo> getCourseListProcess(Map map);

    /**
     * 获取需要互评的课程列表
     * @param map
     * @return
     */
    PageInfo<CourseListMutualVo> getCourseProcessMutual(Map map);

    /**
     * 根据条件获取课程列表
     * @param map
     * @return
     */
    PageInfo<CourseListAndGroupVo> getCourseListAndGroup(Map map);

    /**
     * 添加新课程
     * @param courseAddRequest
     */
    void addCourse(CourseAddRequestDto courseAddRequest);

    /**
     * 开启互评阶段
     * @param courseId
     */
    void openMutual(Integer courseId);

    /**
     * 获取小组长评分课程列表
     * @param map
     * @return
     */
    PageInfo<CourseListLeaderVo> getCourseLeader(Map map);

    /**
     * 获取最终项目评分课程列表
     * @param map
     * @return
     */
    PageInfo<CourseListTeacherVo> getCourseTeacher(Map map);

    /**
     * 获取小组评分课程列表
     * @param map
     * @return
     */
    PageInfo<CourseListGroupVo> getCourseListToGroupScore(Map map);

}
