package com.fcy.scoreservice.service.impl;

import com.fcy.scoreservice.entity.SysLog;
import com.fcy.scoreservice.entity.User;
import com.fcy.scoreservice.entity.dto.GroupAddRequestDto;
import com.fcy.scoreservice.entity.dto.GroupEditRequestDto;
import com.fcy.scoreservice.entity.vo.GroupEditVo;
import com.fcy.scoreservice.entity.vo.GroupListVo;
import com.fcy.scoreservice.entity.vo.LeaderVo;
import com.fcy.scoreservice.entity.vo.UserGroupVo;
import com.fcy.scoreservice.enums.OperationEnum;
import com.fcy.scoreservice.mapper.GroupMapper;
import com.fcy.scoreservice.mapper.UserMapper;
import com.fcy.scoreservice.service.GroupService;
import com.fcy.scoreservice.service.LogService;
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
 * @Date: 2021/3/30 12:27
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private LogService logService;

    @Override
    public List<UserGroupVo> getStudentList(Integer classId, Integer courseId) {
        return userMapper.getStudentList(classId, courseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GroupListVo> addGroup(GroupAddRequestDto groupAddRequest) {
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_GROUP)
                        .operation("insert new group")
                        .build());
        // 新增小组
        groupMapper.addGroup(groupAddRequest);
        List<String> members = groupAddRequest.getMembers();
        members.add(groupAddRequest.getLeaderId());
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_GROUP_STUDENT)
                        .operation("insert student into group")
                        .build());
        groupMapper.addStudentList(groupAddRequest.getGroupId(), members);
        List<Integer> courseIds = new ArrayList<>();
        courseIds.add(groupAddRequest.getCourseId());
        // 返回新的小组列表
        return groupMapper.getGroupList(groupAddRequest.getClassId(), courseIds);
    }

    @Override
    public GroupEditVo getGroupById(Integer groupId) {
        GroupEditVo groupEditVo = groupMapper.getGroupById(groupId);
        groupEditVo.setMembers(groupEditVo.getMemberList()
                .stream().map(UserGroupVo::getUserId).collect(Collectors.toList()));
        return groupEditVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> editGroup(GroupEditRequestDto groupEditRequest) {
        Integer groupId = groupEditRequest.getGroupId();
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_GROUP)
                        .operation("update group leader_id")
                        .build());
        groupMapper.editGroup(groupEditRequest);
        List<String> members = groupEditRequest.getMembers();
        members.add(groupEditRequest.getLeaderId());
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_GROUP_STUDENT)
                        .operation("delete")
                        .build());
        groupMapper.deleteStudent(groupId);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_GROUP_STUDENT)
                        .operation("insert student into group")
                        .build());
        groupMapper.addStudentList(groupId, members);
        // 返回小组成员
        GroupEditVo groupEditVo = groupMapper.getGroupById(groupId);
        return groupEditVo.getMemberList().stream()
                .map(UserGroupVo::getTrueName).collect(Collectors.toList());
    }

    @Override
    public List<LeaderVo> getLeaderList(Integer classId, Integer courseId) {
        return groupMapper.getLeaderList(classId, courseId);
    }
}
