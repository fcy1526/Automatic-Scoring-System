package com.fcy.scoreservice.mapper;

import com.fcy.scoreservice.entity.Stage;
import com.fcy.scoreservice.entity.dto.CourseAddRequestDto;
import com.fcy.scoreservice.entity.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Describe: 
 * @Author: fuchenyang 
 * @Date: 2021/3/28 21:24
 */
@Mapper
public interface CourseMapper {
    List<CourseListVo> getCourseList(Map map);

    List<CourseListAndGroupVo> getCourseListAndGroup(Map map);

    List<CourseListProcessVo> getCourseListProcess(Map<String, Object> map);

    void addCourse(CourseAddRequestDto courseAddRequest);

    void addCourseClass(List<Integer> classIds, Integer courseId);

    List<String> getClassById(Integer courseId);

    List<Stage> getStageById(Integer courseId);

    void setCurrentStage(Integer courseId, String status, Integer currentStage);

    Integer getStageNum(Integer courseId);

    List<CourseListMutualVo> getCourseListMutual(Map map);

    List<CourseListLeaderVo> getCourseTeacher(String userId);

    List<CourseListLeaderVo> getCourseLeader(String userId);

    List<CourseListTeacherVo> getCourseListTeacher(Map<String, Object> query);

    List<CourseListGroupVo> getCourseListToGroupScore(Map<String, Object> query);

    List<Integer> getClassIdsById(Integer courseId);

    Integer getLeaderScoreCount(Integer courseId);

    Integer getLeaderTotalCount(Integer courseId);

    Integer getLeaderScoreCountByLeader(Integer courseId, List<String> members);

    Integer getMutualCount(List<Integer> stageIds);

    void complete(Integer courseId);
}
