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
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_STUDENT_SCORE)
                        .operation("????????????")
                        .build());
        // ?????????????????????????????? https://blog.csdn.net/Soda_lw/article/details/88849259
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
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_MUTUAL_SCORE)
                        .operation("????????????")
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
            // ??????????????????
            return !studentLeaderVo.getUserId().equalsIgnoreCase(userId);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLeaderScore(Integer courseId, List<StudentScore> studentScores) {
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_LEADER_SCORE)
                        .operation("????????????")
                        .build());
        studentScores.stream().forEach(studentScore -> {
            scoreMapper.saveLeaderScore(studentScore.getUserId(),courseId,studentScore.getScore());
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTeacherScore(Integer courseId, List<StudentScore> studentScores) {
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_TEACHER_SCORE)
                        .operation("????????????")
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
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_GROUP_SCORE)
                        .operation("????????????")
                        .build());
        List<GroupScore> groupScores = new ArrayList<>();
        // ??????????????????
        groupScoreDto.getLeaderScore().stream().forEach(map -> {
            GroupScore groupScore = GroupScore.builder()
                    .scoreId((String) map.get("userId"))
                    .stageId(stageId)
                    .groupId(groupId)
                    .score((Integer) map.get("score"))
                    .build();
            groupScores.add(groupScore);
        });
        // ????????????
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
        // ???????????????
        int count = groupScores.stream().mapToInt(GroupScore::getScore).sum();
        double avg = new BigDecimal((float) count / groupScores.size())
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_AVG_GROUP_SCORE)
                        .operation("?????????????????????")
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
                // ????????????
                groupScoreVo.setTeacherScore(groupScore.getScore());
            } else {
                // ?????????????????????
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
            throw new AutomaticScoringSystemException(HttpStatus.BAD_REQUEST, "?????????????????????????????????????????????????????????");
        }
        // ??????id??????
        List<String> userList = userMapper.getStudentListByCourseId(courseId);
        // ??????id??????
        List<Integer> stageIds = stageMapper.getStageIds(courseId);
        // ????????????????????????
        List<StudentScore> studentScores = scoreMapper.getStudentScoreList(stageIds);
        // ??????????????????
        List<AvgGroupScore> groupScores = scoreMapper.getAvgGroupScore(stageIds);
        // ??????????????????
        List<MutualScore> mutualScores = scoreMapper.getMutualScore(stageIds);
        // ?????????????????????
        List<LeaderScore> leaderScores = scoreMapper.getLeaderScore(courseId);
        // ????????????????????????
        List<TeacherScore> teacherScores = scoreMapper.getTeacherScore(courseId);

        // ????????????????????????
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
        // ??????????????????
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
        // ??????????????????
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
        // ??????????????????
        Integer mutualCount = courseMapper.getMutualCount(stageIds);
        // ???????????????map
        Map<String, Integer> leaderScoreMap = leaderScores.stream()
                .collect(Collectors.toMap(LeaderScore::getStudentId, LeaderScore::getScore));
        // ??????????????????map
        Map<String, Integer> teacherScoreMap = teacherScores.stream()
                .collect(Collectors.toMap(TeacherScore::getStudentId, TeacherScore::getScore));
        // ????????????
        Map<Integer, GroupDto> groupDtoMap = groupMapper.getAllGroupList(courseId)
                .stream().collect(Collectors.toMap(GroupDto::getGroupId, groupDto -> {
                    groupDto.calCount();
                    return groupDto;
                }));
        // ??????id - ??????id??????
        List<GroupStudentDto> groupStudentDtos = groupMapper.userForGroup(courseId);
        Map<String, Integer> userForGroup = groupStudentDtos.stream()
                .collect(Collectors.toMap(GroupStudentDto::getUserId, GroupStudentDto::getGroupId));
        // ?????????
        Integer stageNum = stageIds.size();

        // ????????????
        List<TotalScore> totalScores = new ArrayList<>();

        // ???????????????????????????
        userList.stream().forEach(userId -> {
            // ?????????????????????
            Integer studentTotalScore = studentScoreMap.get(userId);
            // ?????????????????????
            double studentScore = new BigDecimal((float) studentTotalScore / stageNum)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            // ??????id
            Integer groupId = userForGroup.get(userId);
            // ???????????????
            Integer groupTotalScore = groupScoreMap.get(groupId);
            // ???????????????
            double groupScore = new BigDecimal((float) groupTotalScore / stageNum)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            // ??????????????????
            Integer mutualTotalScore = mutualScoreMap.get(userId);
            // ?????????????????????
            Integer memberNum = groupDtoMap.get(groupId).getCount();
            // ?????????????????????
            double mutualScore = new BigDecimal((float) mutualTotalScore / (memberNum - 1) / mutualCount)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            // ???????????????
            Integer leaderScore = leaderScoreMap.get(userId);

            // ??????????????????
            Integer teacherScore = teacherScoreMap.get(userId);

            // ????????????
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
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_TOTAL_SCORE)
                        .operation("????????????")
                        .build());
        // ????????????
        scoreMapper.saveTotalScore(totalScores);
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_PROPORTION)
                        .operation("????????????")
                        .build());
        scoreMapper.saveProportion(courseId, proportionDto);
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_COURSE)
                        .operation("????????????")
                        .build());
        // ???????????????????????????
        courseMapper.complete(courseId);
    }

    @Override
    public PageInfo<UserScoreVo> getUserScore(Map map) {
        String userId = AuthUtil.getCurrentUserid();
        map.put("userId", userId);
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        // ????????????
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
