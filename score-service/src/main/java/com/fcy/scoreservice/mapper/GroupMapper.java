package com.fcy.scoreservice.mapper;

import com.fcy.scoreservice.entity.dto.*;
import com.fcy.scoreservice.entity.vo.GroupEditVo;
import com.fcy.scoreservice.entity.vo.GroupListVo;
import com.fcy.scoreservice.entity.vo.LeaderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/30 13:25
 */
@Mapper
public interface GroupMapper {
    List<GroupListVo> getGroupList(Integer classId, List<Integer> courseIds);

    List<String> getGroupListById(Integer groupId);

    void addGroup(GroupAddRequestDto groupAddRequest);

    void addStudentList(Integer groupId, List<String> members);

    GroupEditVo getGroupById(Integer groupId);

    void editGroup(GroupEditRequestDto groupEditRequest);

    void deleteStudent(Integer groupId);

    List<GroupDto> getAllGroupList(Integer courseId);

    Integer getGroupCount(Integer courseId, Integer classId);

    List<LeaderVo> getLeaderList(Integer classId, Integer courseId);

    List<Integer> getGroupIdList(Integer classId, Integer courseId);

    List<LeaderAndGroupDto> getLeaderAndGroupList(Integer classId, Integer courseId);

    Integer getGroupIdByLeaderId(String leaderId, Integer courseId);

    List<String> getMembers(String userId, Integer courseId);

    List<GroupStudentDto> userForGroup(Integer courseId);
}
