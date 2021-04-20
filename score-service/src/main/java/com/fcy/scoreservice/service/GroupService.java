package com.fcy.scoreservice.service;

import com.fcy.scoreservice.entity.User;
import com.fcy.scoreservice.entity.dto.GroupAddRequestDto;
import com.fcy.scoreservice.entity.dto.GroupEditRequestDto;
import com.fcy.scoreservice.entity.vo.GroupEditVo;
import com.fcy.scoreservice.entity.vo.GroupListVo;
import com.fcy.scoreservice.entity.vo.LeaderVo;
import com.fcy.scoreservice.entity.vo.UserGroupVo;

import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/30 12:27
 */
public interface GroupService {
    /**
     * 根据班级id，课程id获取未分组的学生列表
     * @param classId
     * @param courseId
     * @return
     */
    List<UserGroupVo> getStudentList(Integer classId, Integer courseId);

    /**
     * 添加小组
     * @param groupAddRequest
     * @return
     */
    List<GroupListVo> addGroup(GroupAddRequestDto groupAddRequest);

    /**
     * 根据id获取小组信息
     * @param groupId
     * @return
     */
    GroupEditVo getGroupById(Integer groupId);

    /**
     * 修改小组
     * @param groupEditRequest
     */
    List<String> editGroup(GroupEditRequestDto groupEditRequest);

    /**
     * 获取小组长列表
     * @param classId
     * @param courseId
     * @return
     */
    List<LeaderVo> getLeaderList(Integer classId, Integer courseId);
}
