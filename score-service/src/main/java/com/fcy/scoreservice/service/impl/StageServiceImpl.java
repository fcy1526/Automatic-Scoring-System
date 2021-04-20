package com.fcy.scoreservice.service.impl;

import com.fcy.scoreservice.entity.GroupScore;
import com.fcy.scoreservice.entity.SysLog;
import com.fcy.scoreservice.entity.User;
import com.fcy.scoreservice.entity.dto.LeaderAndGroupDto;
import com.fcy.scoreservice.entity.dto.StageStartDto;
import com.fcy.scoreservice.entity.dto.StageStopDto;
import com.fcy.scoreservice.enums.CourseStatus;
import com.fcy.scoreservice.enums.OperationEnum;
import com.fcy.scoreservice.mapper.*;
import com.fcy.scoreservice.service.LogService;
import com.fcy.scoreservice.service.StageService;
import com.fcy.scoreservice.util.AuthUtil;
import com.fcy.scoreservice.util.DateUtil;
import com.fcy.scoreservice.util.SystemTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/30 22:10
 */
@Service
public class StageServiceImpl implements StageService {

    @Autowired
    private StageMapper stageMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private LogService logService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopCurrentStage(StageStopDto stageStop) {
        Integer courseId = stageStop.getCourseId();
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_STAGE)
                        .operation("结束课程阶段，阶段: " + stageStop.getCurrentStage())
                        .build());
        stageStop.setEndTime(DateUtil.getCurrentDate());
        // 保存结束时间
        stageMapper.stopCurrentStage(stageStop);
        Integer stageNum = courseMapper.getStageNum(courseId);
        String status = CourseStatus.WAIT.getStatus();
        Integer nextStage = stageStop.getCurrentStage() + 1;
        if (stageNum == stageStop.getCurrentStage()) {
            // 最终阶段 当前课程已结束
            status = CourseStatus.FINISH.getStatus();
            // 获取学生列表
            List<String> studentList = userMapper.getStudentListByCourseId(courseId);
            // 保存日志
            logService.logBuild(
                    SysLog.builder()
                            .opertype(OperationEnum.INSERT)
                            .opertable(SystemTable.T_LEADER_SCORE)
                            .operation("init")
                            .build());
            // 开启小组长评分
            scoreMapper.initLeaderScore(courseId, studentList);
            // 保存日志
            logService.logBuild(
                    SysLog.builder()
                            .opertype(OperationEnum.INSERT)
                            .opertable(SystemTable.T_TEACHER_SCORE)
                            .operation("init")
                            .build());
            // 开启最终项目评分
            scoreMapper.initTeacherScore(courseId,studentList);
        }
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_COURSE)
                        .operation("改变课程状态: " + status)
                        .build());
        // 改变课程状态
        courseMapper.setCurrentStage(courseId, status, nextStage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startNewStage(StageStartDto stageStart) {
        Integer nextStage = stageStart.getCurrentStage();
        if (nextStage == 0) {
            // 新阶段
            nextStage ++;
            stageStart.setCurrentStage(nextStage);
            // 阶段id列表
            List<Integer> stageIds = stageMapper.getStageIds(stageStart.getCourseId());
            // 班级id列表
            List<Integer> classIds = courseMapper.getClassIdsById(stageStart.getCourseId());
            // 获取小组评分实体类列表
            List<GroupScore> groupScores = getGroupScoreList(stageIds, classIds, stageStart.getCourseId());
            // 保存日志
            logService.logBuild(
                    SysLog.builder()
                            .opertype(OperationEnum.INSERT)
                            .opertable(SystemTable.T_GROUP_SCORE)
                            .operation("初始化小组评分")
                            .build());
            // 初始化小组评分
            scoreMapper.initGroupScore(groupScores);
            // 保存日志
            logService.logBuild(
                    SysLog.builder()
                            .opertype(OperationEnum.INSERT)
                            .opertable(SystemTable.T_AVG_GROUP_SCORE)
                            .operation("初始化小组平均分")
                            .build());
            // 初始化小组平均分
            List<Integer> groupIds = groupScores.stream().map(GroupScore::getGroupId).distinct().collect(Collectors.toList());
            scoreMapper.initAvgGroupScore(stageIds, groupIds);
        }
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_STAGE)
                        .operation("开启新课程阶段，阶段数: " + stageStart.getCurrentStage())
                        .build());
        stageStart.setStartTime(DateUtil.getCurrentDate());
        // 保存开启时间
        stageMapper.startNewStage(stageStart);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_COURSE)
                        .operation("改变课程状态: " + CourseStatus.PROCESS)
                        .build());
        // 改变课程状态
        courseMapper.setCurrentStage(stageStart.getCourseId(), CourseStatus.PROCESS.getStatus(), nextStage);
    }

    /**
     * 获取小组评分实体类列表
     * @param stageIds
     * @param classIds
     * @param courseId
     * @return
     */
    private List<GroupScore> getGroupScoreList(List<Integer> stageIds, List<Integer> classIds, Integer courseId) {
        List<GroupScore> groupScores = new ArrayList<>();
        // 教师信息
        User teacher = userMapper.findByUserid(AuthUtil.getCurrentUserid());
        for (Integer classId : classIds) {
            List<LeaderAndGroupDto> leaderAndGroupDtos = groupMapper.getLeaderAndGroupList(classId, courseId);
            for (Integer stageId : stageIds) {
                for (LeaderAndGroupDto leaderA : leaderAndGroupDtos) { // 评分人id
                    for (LeaderAndGroupDto leaderB : leaderAndGroupDtos) { // 被评分小组
                        if (!leaderA.getUserId().equals(leaderB.getUserId())) {
                            GroupScore groupScore = GroupScore.builder()
                                    .scoreId(leaderA.getUserId())
                                    .trueName(leaderA.getTrueName())
                                    .type("leader")
                                    .stageId(stageId)
                                    .groupId(leaderB.getGroupId())
                                    .build();
                            groupScores.add(groupScore);
                        }
                    }
                    // 教师评分
                    GroupScore groupScore = GroupScore.builder()
                            .scoreId(teacher.getUserId())
                            .trueName(teacher.getTrueName())
                            .type("teacher")
                            .stageId(stageId)
                            .groupId(leaderA.getGroupId())
                            .build();
                    groupScores.add(groupScore);
                }
            }
        }
        return groupScores;
    }
}
