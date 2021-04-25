package com.fcy.scoreservice.mapper;

import com.fcy.scoreservice.entity.*;
import com.fcy.scoreservice.entity.dto.ProportionDto;
import com.fcy.scoreservice.entity.vo.StudentGroupMutualVo;
import com.fcy.scoreservice.entity.vo.StudentLeaderVo;
import com.fcy.scoreservice.entity.vo.StudentScoreListVo;
import com.fcy.scoreservice.entity.vo.UserScoreVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/31 15:47
 */
@Mapper
public interface ScoreMapper {
    List<StudentScoreListVo> getStudentList(Integer courseId);

    void initStudentScore(List<Map<String, String>> users, List<Integer> stageIds);

    void saveStudentScore(Integer stageId, String userId, Integer score);

    void saveMutualScore(Integer stageId, String scoreId, String targetId, Integer score);

    void mutualScoreInit(Integer stageId, List<Map<String, Object>> mutualGroup);

    List<MutualScore> checkMutual(Integer stageId);

    Map<String, Long> getMutualCount(String userId, Integer stageId);

    List<StudentGroupMutualVo> getStudentGroup(String userId, Integer stageId);

    List<StudentLeaderVo> getStudentLeader(Integer courseId);

    List<StudentLeaderVo> getStudentListLeader(Integer courseId, Integer groupId);

    void saveLeaderScore(String userId, Integer courseId, Integer score);

    void initLeaderScore(Integer courseId, List<String> studentList);

    void saveTeacherScore(String userId, Integer courseId, Integer score);

    void initTeacherScore(Integer courseId, List<String> studentList);

    List<StudentScoreListVo> getFinalStudentList(Integer courseId);

    Integer getGroupScoreCount(Integer stageId);

    Integer getStudentScoreCount(Integer stageId, Integer classId);

    List<GroupScore> getGroupScore(Integer stageId, String leaderId);

    void initGroupScore(List<GroupScore> groupScores);

    void saveGroupScore(GroupScore groupScore);

    void saveAvgGroupScore(Integer groupId, Integer stageId, double avg);

    void initAvgGroupScore(List<Integer> stageIds, List<Integer> groupIds);

    Integer getTeacherScoreCount(Integer classId, Integer courseId);

    List<StudentScore> getStudentScoreList(List<Integer> stageIds);

    List<AvgGroupScore> getAvgGroupScore(List<Integer> stageIds);

    List<MutualScore> getMutualScore(List<Integer> stageIds);

    List<LeaderScore> getLeaderScore(Integer courseId);

    List<TeacherScore> getTeacherScore(Integer courseId);

    void saveTotalScore(List<TotalScore> totalScores);

    void saveProportion(Integer courseId, ProportionDto proportionDto);

    List<UserScoreVo> getUserScore(Map map);

    List<TotalScore> getTotalScore(List<String> userIds);
}
