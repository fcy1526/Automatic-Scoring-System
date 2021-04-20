package com.fcy.scoreservice.service;

import com.fcy.scoreservice.entity.StudentScore;
import com.fcy.scoreservice.entity.dto.GroupScoreDto;
import com.fcy.scoreservice.entity.dto.ProportionDto;
import com.fcy.scoreservice.entity.dto.ScoreClassInfoDto;
import com.fcy.scoreservice.entity.dto.StudentScoreDto;
import com.fcy.scoreservice.entity.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/31 15:46
 */
public interface ScoreService {
    /**
     * 根据班级id获取学生表现评分列表
     * @param scoreClassInfo
     * @return
     */
    List<StudentScoreListVo> getStudentList(ScoreClassInfoDto scoreClassInfo);

    /**
     * 保存学生表现成绩
     * @param studentScoreDto
     */
    void saveStudentScore(StudentScoreDto studentScoreDto);

    /**
     * 获取小组成员信息
     * @param stageId
     * @return
     */
    List<StudentGroupMutualVo> getStudentGroup(Integer stageId);

    /**
     * 保存小组互评成绩
     * @param stageId
     * @param studentScores
     */
    void saveGroupMutual(Integer stageId, List<StudentScore> studentScores);

    /**
     * 获取小组长列表
     * @param courseId
     * @return
     */
    List<StudentLeaderVo> getStudentLeader(Integer courseId);

    /**
     * 获取小组成员信息
     * @param courseId
     * @param groupId
     * @return
     */
    List<StudentLeaderVo> getStudentList(Integer courseId, Integer groupId);

    /**
     * 保存小组长评分
     * @param courseId
     * @param studentScores
     */
    void saveLeaderScore(Integer courseId, List<StudentScore> studentScores);

    /**
     * 保存最终项目评分
     * @param courseId
     * @param studentScores
     */
    void saveTeacherScore(Integer courseId, List<StudentScore> studentScores);

    /**
     * 获取最终项目评分学生列列表
     * @param scoreClassInfo
     * @return
     */
    List<StudentScoreListVo> getFinalStudentList(ScoreClassInfoDto scoreClassInfo);

    /**
     * 获取小组评分
     * @param stageId
     * @param leaderId
     * @return
     */
    GroupScoreVo getGroupScore(Integer stageId, String leaderId);

    /**
     * 保存小组评分
     * @param stageId
     * @param leaderId
     * @param courseId
     * @param groupScoreDto
     */
    void saveGroupScore(Integer stageId, String leaderId, Integer courseId, GroupScoreDto groupScoreDto);

    /**
     * 统计总分
     * @param courseId
     * @param proportionDto
     */
    void count(Integer courseId, ProportionDto proportionDto);

    /**
     * 学生成绩查询
     * @param map
     * @return
     */
    PageInfo<UserScoreVo> getUserScore(Map map);
}
