package com.fcy.scoreservice.service.impl;

import com.fcy.scoreservice.common.Constants;
import com.fcy.scoreservice.entity.*;
import com.fcy.scoreservice.entity.dto.*;
import com.fcy.scoreservice.entity.vo.*;
import com.fcy.scoreservice.enums.OperationEnum;
import com.fcy.scoreservice.exception.AutomaticScoringSystemException;
import com.fcy.scoreservice.feign.UserServiceClient;
import com.fcy.scoreservice.mapper.*;
import com.fcy.scoreservice.service.LogService;
import com.fcy.scoreservice.service.ScoreService;
import com.fcy.scoreservice.util.AuthUtil;
import com.fcy.scoreservice.util.SystemTable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/31 15:45
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private StageMapper stageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private LogService logService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public List<StudentScoreListVo> getStudentList(ScoreClassInfoDto scoreClassInfo) {
        List<UserGroupVo> studentList = userMapper.getAllStudentList(scoreClassInfo.getClassId());
        List<StudentScoreListVo> scoreListVos = new ArrayList<>();
        for (UserGroupVo userGroupVo : studentList) {
            StudentScoreListVo scoreListVo = new StudentScoreListVo();
            BeanUtils.copyProperties(userGroupVo, scoreListVo);
            scoreListVos.add(scoreListVo);
        }
        List<StudentScoreListVo> listVos = scoreMapper.getStudentList(scoreClassInfo.getCourseId());
        scoreListVos.stream().forEach(studentScoreListVo -> {
            Iterator it = listVos.iterator();
            while (it.hasNext()) {
                StudentScoreListVo listVo = (StudentScoreListVo) it.next();
                if (studentScoreListVo.getUserId().equalsIgnoreCase(listVo.getUserId())) {
                    studentScoreListVo.setScore(listVo.getScore());
                    it.remove();
                }
            }
        });
        return scoreListVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStudentScore(StudentScoreDto studentScoreDto) {
        Integer stageId = stageMapper.getStageId(studentScoreDto.getCurrentStage(), studentScoreDto.getCourseId());
        List<StudentScore> studentScores = studentScoreDto.getScoreList();
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_STUDENT_SCORE)
                        .operation("保存评分")
                        .build());
        // 待优化：可用分号隔开 https://blog.csdn.net/Soda_lw/article/details/88849259
        studentScores.stream().forEach(studentScore ->
                scoreMapper.saveStudentScore(stageId, studentScore.getUserId(), studentScore.getScore()));
    }

    @Override
    public List<StudentGroupMutualVo> getStudentGroup(Integer stageId) {
        String userId = AuthUtil.getCurrentUserid();
        return scoreMapper.getStudentGroup(userId,stageId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGroupMutual(Integer stageId, List<StudentScore> studentScores) {
        String userId = AuthUtil.getCurrentUserid();
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_MUTUAL_SCORE)
                        .operation("保存评分")
                        .build());
        studentScores.stream().forEach(studentScore ->
                scoreMapper.saveMutualScore(stageId, userId, studentScore.getUserId(), studentScore.getScore()));
    }

    @Override
    public List<StudentLeaderVo> getStudentLeader(Integer courseId) {
        return scoreMapper.getStudentLeader(courseId);
    }

    @Override
    public List<StudentLeaderVo> getStudentList(Integer courseId, Integer groupId) {
        String userId = AuthUtil.getCurrentUserid();
        return scoreMapper.getStudentListLeader(courseId, groupId).stream().filter(studentLeaderVo -> {
            // 剔除当前用户
            return !studentLeaderVo.getUserId().equalsIgnoreCase(userId);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLeaderScore(Integer courseId, List<StudentScore> studentScores) {
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_LEADER_SCORE)
                        .operation("保存评分")
                        .build());
        studentScores.stream().forEach(studentScore -> {
            scoreMapper.saveLeaderScore(studentScore.getUserId(),courseId,studentScore.getScore());
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTeacherScore(Integer courseId, List<StudentScore> studentScores) {
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_TEACHER_SCORE)
                        .operation("保存评分")
                        .build());
        studentScores.stream().forEach(studentScore -> {
            scoreMapper.saveTeacherScore(studentScore.getUserId(),courseId,studentScore.getScore());
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGroupScore(Integer stageId, String leaderId, Integer courseId, GroupScoreDto groupScoreDto) {
        Integer teacherScore = groupScoreDto.getTeacherScore();
        Integer groupId = groupMapper.getGroupIdByLeaderId(leaderId, courseId);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_GROUP_SCORE)
                        .operation("保存评分")
                        .build());
        List<GroupScore> groupScores = new ArrayList<>();
        // 其他小组评分
        groupScoreDto.getLeaderScore().stream().forEach(map -> {
            GroupScore groupScore = GroupScore.builder()
                    .scoreId((String) map.get("userId"))
                    .stageId(stageId)
                    .groupId(groupId)
                    .score((Integer) map.get("score"))
                    .build();
            groupScores.add(groupScore);
        });
        // 教师评分
        GroupScore groupScore = GroupScore.builder()
                .scoreId(AuthUtil.getCurrentUserid())
                .stageId(stageId)
                .groupId(groupId)
                .score(teacherScore)
                .build();
        groupScores.add(groupScore);
        groupScores.stream().forEach(gs -> {
            scoreMapper.saveGroupScore(gs);
        });
        // 记录平均分
        int count = groupScores.stream().mapToInt(GroupScore::getScore).sum();
        double avg = new BigDecimal((float) count / groupScores.size())
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_AVG_GROUP_SCORE)
                        .operation("保存小组平均分")
                        .build());
        scoreMapper.saveAvgGroupScore(groupId, stageId, avg);
    }

    @Override
    public List<StudentScoreListVo> getFinalStudentList(ScoreClassInfoDto scoreClassInfo) {
        List<UserGroupVo> studentList = userMapper.getAllStudentList(scoreClassInfo.getClassId());
        List<StudentScoreListVo> scoreListVos = new ArrayList<>();
        for (UserGroupVo userGroupVo : studentList) {
            StudentScoreListVo scoreListVo = new StudentScoreListVo();
            BeanUtils.copyProperties(userGroupVo, scoreListVo);
            scoreListVos.add(scoreListVo);
        }
        List<StudentScoreListVo> listVos = scoreMapper.getFinalStudentList(scoreClassInfo.getCourseId());
        scoreListVos.stream().forEach(studentScoreListVo -> {
            Iterator it = listVos.iterator();
            while (it.hasNext()) {
                StudentScoreListVo listVo = (StudentScoreListVo) it.next();
                if (studentScoreListVo.getUserId().equalsIgnoreCase(listVo.getUserId())) {
                    studentScoreListVo.setScore(listVo.getScore());
                    it.remove();
                }
            }
        });
        return scoreListVos;
    }

    @Override
    public GroupScoreVo getGroupScore(Integer stageId, String leaderId) {
        List<GroupScore> groupScores = scoreMapper.getGroupScore(stageId, leaderId);
        GroupScoreVo groupScoreVo = new GroupScoreVo();
        groupScores.stream().forEach(groupScore -> {
            String type = groupScore.getType();
            if (type.equalsIgnoreCase("teacher")) {
                // 教师评分
                groupScoreVo.setTeacherScore(groupScore.getScore());
            } else {
                // 其他小组长评分
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", groupScore.getScoreId());
                map.put("trueName", groupScore.getTrueName());
                map.put("score", groupScore.getScore());
                List<Map<String, Object>> maps = groupScoreVo.getLeaderScore();
                if (CollectionUtils.isEmpty(maps)) {
                    maps = new ArrayList<>();
                }
                maps.add(map);
                groupScoreVo.setLeaderScore(maps);
            }
        });
        return groupScoreVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void count(Integer courseId, ProportionDto proportionDto) {
        if (proportionDto.getSum() != Constants.PROPORTION_SUM) {
            throw new AutomaticScoringSystemException(HttpStatus.BAD_REQUEST, "总评分占比不正确，请重新调整评分占比。");
        }
        // 学生id列表
        List<String> userList = userMapper.getStudentListByCourseId(courseId);
        // 阶段id列表
        List<Integer> stageIds = stageMapper.getStageIds(courseId);
        // 学生表现评分列表
        List<StudentScore> studentScores = scoreMapper.getStudentScoreList(stageIds);
        // 小组评分列表
        List<AvgGroupScore> groupScores = scoreMapper.getAvgGroupScore(stageIds);
        // 小组互评列表
        List<MutualScore> mutualScores = scoreMapper.getMutualScore(stageIds);
        // 小组长评分列表
        List<LeaderScore> leaderScores = scoreMapper.getLeaderScore(courseId);
        // 最终项目评分列表
        List<TeacherScore> teacherScores = scoreMapper.getTeacherScore(courseId);

        // 学生表现评分求和
        Map<String,Integer> studentScoreMap = new HashMap<>();
        studentScores.stream().forEach(studentScore -> {
            String userId = studentScore.getUserId();
            Integer score = studentScoreMap.get(userId);
            if (null == score) {
                score = 0;
            }
            score += studentScore.getScore();
            studentScoreMap.put(userId, score);
        });
        // 小组评分求和
        Map<Integer, Integer> groupScoreMap = new HashMap<>();
        groupScores.stream().forEach(avgGroupScore -> {
            Integer groupId = avgGroupScore.getGroupId();
            Integer score = groupScoreMap.get(groupId);
            if (null == score) {
                score = 0;
            }
            score += avgGroupScore.getScore();
            groupScoreMap.put(groupId, score);
        });
        // 小组互评求和
        Map<String, Integer> mutualScoreMap = new HashMap<>();
        mutualScores.stream().forEach(mutualScore -> {
            String userId = mutualScore.getTargetId();
            Integer score = mutualScoreMap.get(userId);
            if (null == score) {
                score = 0;
            }
            score += mutualScore.getScore();
            mutualScoreMap.put(userId, score);
        });
        // 小组互评次数
        Integer mutualCount = courseMapper.getMutualCount(stageIds);
        // 小组长评分map
        Map<String, Integer> leaderScoreMap = leaderScores.stream()
                .collect(Collectors.toMap(LeaderScore::getStudentId, LeaderScore::getScore));
        // 最终项目评分map
        Map<String, Integer> teacherScoreMap = teacherScores.stream()
                .collect(Collectors.toMap(TeacherScore::getStudentId, TeacherScore::getScore));
        // 小组列表
        Map<Integer, GroupDto> groupDtoMap = groupMapper.getAllGroupList(courseId)
                .stream().collect(Collectors.toMap(GroupDto::getGroupId, groupDto -> {
                    groupDto.calCount();
                    return groupDto;
                }));
        // 学生id - 小组id映射
        List<GroupStudentDto> groupStudentDtos = groupMapper.userForGroup(courseId);
        Map<String, Integer> userForGroup = groupStudentDtos.stream()
                .collect(Collectors.toMap(GroupStudentDto::getUserId, GroupStudentDto::getGroupId));
        // 阶段数
        Integer stageNum = stageIds.size();

        // 总分列表
        List<TotalScore> totalScores = new ArrayList<>();

        // 循环计算学生总评分
        userList.stream().forEach(userId -> {
            // 学生表现总评分
            Integer studentTotalScore = studentScoreMap.get(userId);
            // 学生表现平均分
            double studentScore = new BigDecimal((float) studentTotalScore / stageNum)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            // 小组id
            Integer groupId = userForGroup.get(userId);
            // 小组总评分
            Integer groupTotalScore = groupScoreMap.get(groupId);
            // 小组平均分
            double groupScore = new BigDecimal((float) groupTotalScore / stageNum)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            // 小组互评总分
            Integer mutualTotalScore = mutualScoreMap.get(userId);
            // 所在小组总人数
            Integer memberNum = groupDtoMap.get(groupId).getCount();
            // 小组互评平均分
            double mutualScore = new BigDecimal((float) mutualTotalScore / (memberNum - 1) / mutualCount)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            // 小组长评分
            Integer leaderScore = leaderScoreMap.get(userId);

            // 最终项目评分
            Integer teacherScore = teacherScoreMap.get(userId);

            // 计算总分
            double score = new BigDecimal((
                    studentScore * proportionDto.getStudentScore() +
                    groupScore * proportionDto.getGroupScore() +
                    mutualScore * proportionDto.getMutualScore() +
                    leaderScore * proportionDto.getLeaderScore() +
                    teacherScore * proportionDto.getTeacherScore()) / 100)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            TotalScore totalScore = TotalScore.builder()
                    .studentId(userId)
                    .courseId(courseId)
                    .studentScore(studentScore)
                    .groupScore(groupScore)
                    .mutualScore(mutualScore)
                    .leaderScore(leaderScore)
                    .teacherScore(teacherScore)
                    .score(score).build();

            totalScores.add(totalScore);
        });
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_TOTAL_SCORE)
                        .operation("统计总分")
                        .build());
        // 保存评分
        scoreMapper.saveTotalScore(totalScores);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_PROPORTION)
                        .operation("评分占比")
                        .build());
        scoreMapper.saveProportion(courseId, proportionDto);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_COURSE)
                        .operation("课程完成")
                        .build());
        // 设置课程状态为完成
        courseMapper.complete(courseId);
    }

    @Override
    public PageInfo<UserScoreVo> getUserScore(Map map) {
        String userId = AuthUtil.getCurrentUserid();
        map.put("userId", userId);
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        // 设置分页
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return new PageInfo<UserScoreVo>(scoreMapper.getUserScore(map));
    }

    @Override
    public Map<String, List<TotalScore>> getScore(Integer courseId) {
        List<Integer> classIds = courseMapper.getClassIdsById(courseId);
        Map<String, List<TotalScore>> map = new HashMap<>();
        for (Integer classId : classIds) {
            String className = (String) userServiceClient.getClassName(classId).getData();
            List<UserGroupVo> userGroupVos = userMapper.getAllStudentList(classId);
            List<String> userIds = userGroupVos.stream().map(UserGroupVo::getUserId).collect(Collectors.toList());
            List<TotalScore> totalScores = scoreMapper.getTotalScore(userIds);
            map.put(className, totalScores);
        }
        return map;
    }
}
