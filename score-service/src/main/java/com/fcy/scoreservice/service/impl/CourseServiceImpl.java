package com.fcy.scoreservice.service.impl;

import com.fcy.scoreservice.common.Constants;
import com.fcy.scoreservice.entity.*;
import com.fcy.scoreservice.entity.dto.CourseAddRequestDto;
import com.fcy.scoreservice.entity.dto.CourseGetListRequestDto;
import com.fcy.scoreservice.entity.dto.GroupDto;
import com.fcy.scoreservice.entity.dto.LeaderAndGroupDto;
import com.fcy.scoreservice.entity.vo.*;
import com.fcy.scoreservice.enums.CourseStatus;
import com.fcy.scoreservice.enums.OperationEnum;
import com.fcy.scoreservice.exception.AutomaticScoringSystemException;
import com.fcy.scoreservice.feign.UserServiceClient;
import com.fcy.scoreservice.mapper.*;
import com.fcy.scoreservice.service.CourseService;
import com.fcy.scoreservice.service.LogService;
import com.fcy.scoreservice.util.AuthUtil;
import com.fcy.scoreservice.util.DateUtil;
import com.fcy.scoreservice.util.SystemTable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/28 20:57
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StageMapper stageMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private LogService logService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public PageInfo<CourseListVo> getCourseList(Map map) {
        return new PageInfo<CourseListVo>(courseMapper.getCourseList(buildQuery(map)));
    }

    @Override
    public PageInfo<CourseListProcessVo> getCourseListProcess(Map map) {
        Map<String, Object> query = buildQuery(map);
        PageInfo<CourseListProcessVo> pageInfo =
                new PageInfo<CourseListProcessVo>(courseMapper.getCourseListProcess(query));
        List<CourseListProcessVo> listProcessVos = pageInfo.getList();
        Integer classId = (Integer) query.get("classId");
        // 获取班级人数
        Integer classCount = (Integer) userServiceClient.getTotalCount(classId).getData();
        // 获取评分人数
        listProcessVos.stream().forEach(courseListProcessVo -> {
            Integer stageId = courseListProcessVo.getCurrentStageId();
            Integer scoreCount = scoreMapper.getStudentScoreCount(stageId, classId);
            courseListProcessVo.setClassCount(classCount);
            courseListProcessVo.setScoreCount(scoreCount);
        });
        return pageInfo;
    }

    @Override
    public PageInfo<CourseListGroupVo> getCourseListToGroupScore(Map map) {
        Map<String, Object> query = buildQuery(map);
        PageInfo<CourseListGroupVo> pageInfo =
                new PageInfo<CourseListGroupVo>(courseMapper.getCourseListToGroupScore(query));
        List<CourseListGroupVo> courseListGroupVos = pageInfo.getList();
        Integer classId = (Integer) query.get("classId");
        courseListGroupVos.stream().forEach(courseListGroupVo -> {
            Integer courseId = courseListGroupVo.getCourseId();
            Integer stageId = courseListGroupVo.getCurrentStageId();
            courseListGroupVo.setGroupCount(groupMapper.getGroupCount(courseId, classId));
            courseListGroupVo.setScoreCount(scoreMapper.getGroupScoreCount(stageId));
        });
        return pageInfo;
    }

    @Override
    public PageInfo<CourseListMutualVo> getCourseProcessMutual(Map map) {
        String userId = AuthUtil.getCurrentUserid();
        map.put("userId", userId);
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        // 设置分页
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        PageInfo<CourseListMutualVo> pageInfo =
                new PageInfo<CourseListMutualVo>(courseMapper.getCourseListMutual(map));
        List<CourseListMutualVo> listMutualVos = pageInfo.getList();
        listMutualVos.stream().forEach(courseListMutualVo -> {
            Map<String, Long> countMap =
                    scoreMapper.getMutualCount(userId, courseListMutualVo.getStageId());
            courseListMutualVo.setGroupCount(countMap.get("groupCount").intValue());
            courseListMutualVo.setScoreCount(countMap.get("scoreCount").intValue());
        });
        return pageInfo;
    }

    @Override
    public PageInfo<CourseListAndGroupVo> getCourseListAndGroup(Map map) {
        Map<String, Object> query = buildQuery(map);
        PageInfo<CourseListAndGroupVo> pageInfo =
                new PageInfo<CourseListAndGroupVo>(courseMapper.getCourseListAndGroup(query));
        List<CourseListAndGroupVo> courseListVos = pageInfo.getList();
        List<Integer> intList = courseListVos.stream().map(CourseListAndGroupVo::getCourseId).collect(Collectors.toList());
        List<GroupListVo> groupListVos = groupMapper.getGroupList((Integer) query.get("classId"), intList);
        for (CourseListAndGroupVo courseListAndGroupVo : courseListVos) {
            courseListAndGroupVo.setGroupList(groupListVos.stream().filter(
                    groupListVo -> groupListVo.getCourseId().equals(courseListAndGroupVo.getCourseId()))
                    .collect(Collectors.toList()));
        }
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCourse(CourseAddRequestDto courseAddRequest) {
        courseAddRequest.setTeacherId(AuthUtil.getCurrentUserid());
        courseAddRequest.setCreateTime(DateUtil.getCurrentDate());
        courseAddRequest.setStatus(CourseStatus.WAIT);
        courseAddRequest.setCurrentStage(Constants.CURRENT_STAGE_INSERT);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_COURSE)
                        .operation("新增课程，课程名称: [" + courseAddRequest.getName() + "]")
                        .build());
        // 添加课程
        courseMapper.addCourse(courseAddRequest);
        // 关联班级
        if (!StringUtils.isEmpty(courseAddRequest.getClassIds())) {
            // 保存日志
            logService.logBuild(
                    SysLog.builder()
                            .opertype(OperationEnum.INSERT)
                            .opertable(SystemTable.T_COURSE_CLASS)
                            .operation("insert t_course_class")
                            .build());
            courseMapper.addCourseClass(courseAddRequest.getClassIds(), courseAddRequest.getCourseId());
        }
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_STAGE)
                        .operation("新增阶段，阶段数: " + courseAddRequest.getStageNum())
                        .build());
        // 新增阶段
        stageMapper.addStage(courseAddRequest.getCourseId(),courseAddRequest.getStageNum());
        // 获取新增阶段id
        List<Integer> stageIds = stageMapper.getStageIds(courseAddRequest.getCourseId());
        List<Map<String, String>> users = userMapper.getStudentIds(courseAddRequest.getClassIds());
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_STUDENT_SCORE)
                        .operation("初始化学生表现评分")
                        .build());
        // 初始化学生表现评分
        scoreMapper.initStudentScore(users, stageIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void openMutual(Integer courseId) {
        // 获取当前阶段id
        Integer stageId = stageMapper.getCurrentStageId(courseId);
        // 验证是否已开启互评
        checkMutual(stageId);
        // 获取所有小组
        List<GroupDto> groupList = groupMapper.getAllGroupList(courseId);
        List<Map<String, Object>> mutualGroup = new ArrayList<>();
        groupList.stream().forEach(groupDto -> {
            List<String> members = groupDto.getMembers();
            for (String memberA : members) {
                for (String memberB : members) {
                    if (!memberA.equals(memberB)) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("scoreId", memberA);
                        map.put("targetId", memberB);
                        map.put("groupId", groupDto.getGroupId());
                        mutualGroup.add(map);
                    }
                }
            }
        });
        scoreMapper.mutualScoreInit(stageId, mutualGroup);
    }

    @Override
    public PageInfo<CourseListLeaderVo> getCourseLeader(Map map) {
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        // 设置分页
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        // 判断是否为教师
        // 获取用户认证信息。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        PageInfo<CourseListLeaderVo> pageInfo = null;
        for (GrantedAuthority simpleGrantedAuthority : authentication.getAuthorities()) {
            if (simpleGrantedAuthority.getAuthority().equalsIgnoreCase("Teacher")) {
                // 教师 获取所教学课程
                pageInfo = new PageInfo<CourseListLeaderVo>(courseMapper.getCourseTeacher(userId));
                // 获取所有小组数
                List<CourseListLeaderVo> courseListLeaderVos = pageInfo.getList();
                courseListLeaderVos.stream().forEach(courseListLeaderVo -> {
                    Integer scoreCount = courseMapper.getLeaderScoreCount(courseListLeaderVo.getCourseId());
                    Integer totalCount = courseMapper.getLeaderTotalCount(courseListLeaderVo.getCourseId());
                    courseListLeaderVo.setScoreCount(scoreCount);
                    courseListLeaderVo.setTotalCount(totalCount);
                });
                return pageInfo;
            }
        }
        pageInfo = new PageInfo<CourseListLeaderVo>(courseMapper.getCourseLeader(userId));
        // 获取所有小组数
        List<CourseListLeaderVo> courseListLeaderVos = pageInfo.getList();
        courseListLeaderVos.stream().forEach(courseListLeaderVo -> {
            List<String> members = groupMapper.getMembers(userId, courseListLeaderVo.getCourseId());
            courseListLeaderVo.setTotalCount(members.size());
            Integer scoreCount = courseMapper.getLeaderScoreCountByLeader(courseListLeaderVo.getCourseId(), members);
            courseListLeaderVo.setScoreCount(scoreCount);
        });
        return pageInfo;
    }

    @Override
    public PageInfo<CourseListTeacherVo> getCourseTeacher(Map map) {
        Map<String, Object> query = buildQuery(map);
        Integer classId = (Integer) query.get("classId");
        PageInfo<CourseListTeacherVo> pageInfo =
                new PageInfo<CourseListTeacherVo>(courseMapper.getCourseListTeacher(query));
        List<CourseListTeacherVo> courseListTeacherVos = pageInfo.getList();
        courseListTeacherVos.stream().forEach(courseListTeacherVo -> {
            Integer totalCount = userMapper.getStudentCount(classId);
            courseListTeacherVo.setTotalCount(totalCount);
            Integer scoreCount = scoreMapper.getTeacherScoreCount(classId, courseListTeacherVo.getCourseId());
            courseListTeacherVo.setScoreCount(scoreCount);
        });
        return pageInfo;
    }

    /**
     * 从dto中获取要查询的字段，填入map中
     * @param map
     */
    public static Map<String, Object> buildQuery(Map map) {
        CourseGetListRequestDto courseGetListRequest = (CourseGetListRequestDto) map.get("query");
        Integer classId = courseGetListRequest.getClassId();
        String name = courseGetListRequest.getName();
        // 填充查询条件
        Map<String, Object> query = new HashMap<>();
        query.put("classId", classId);
        query.put("name", name);
        // 添加教师id
        query.put("teacherId", AuthUtil.getCurrentUserid());
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        // 添加状态
        if (map.containsKey("status")) {
            String status = (String) map.get("status");
            query.put("status", status);
        }
        // 设置分页
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return query;
    }

    public void checkMutual(Integer stageId) {
        List<MutualScore> mutualScores = scoreMapper.checkMutual(stageId);
        if (mutualScores.size() > 0) {
            throw new AutomaticScoringSystemException(HttpStatus.BAD_REQUEST, "已开启互评");
        }
    }
}
