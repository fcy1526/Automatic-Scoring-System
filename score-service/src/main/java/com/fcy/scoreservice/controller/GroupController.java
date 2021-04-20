package com.fcy.scoreservice.controller;

import com.fcy.scoreservice.entity.dto.GroupAddRequestDto;
import com.fcy.scoreservice.entity.dto.GroupEditRequestDto;
import com.fcy.scoreservice.service.GroupService;
import com.fcy.scoreservice.util.ResultUtil;
import com.fcy.scoreservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Describe:
 * @Author: fuchenyang 
 * @Date: 2021/3/30 12:19
 */
@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * 根据班级id，课程id获取未分组的学生列表
     * @param classId
     * @param courseId
     * @return
     */
    @GetMapping("student/{classId}/{courseId}")
    public Result<Object> getStudentList(@PathVariable("classId") Integer classId,
                                         @PathVariable("courseId") Integer courseId) {
        return ResultUtil.success(groupService.getStudentList(classId, courseId));
    }

    /**
     * 根据小组id获取小组信息
     * @param groupId
     * @return
     */
    @GetMapping("{groupId}")
    public Result<Object> getGroupById(@PathVariable("groupId") Integer groupId) {
        return ResultUtil.success(groupService.getGroupById(groupId));
    }

    /**
     * 获取小组长列表
     * @param classId
     * @param courseId
     * @return
     */
    @GetMapping("leader")
    public Result<Object> getLeaderList(@RequestParam("classId") Integer classId,
                                        @RequestParam("courseId") Integer courseId) {
        return ResultUtil.success(groupService.getLeaderList(classId, courseId));
    }

//    /**
//     * 获取小组列表
//     * @param courseId
//     * @return
//     */
//    @GetMapping("{classId}/{courseId}")
//    public Result<Object> getGroupList(@PathVariable("classId") Integer classId,
//                                       @PathVariable("courseId") Integer courseId) {
//        return ResultUtil.success(groupService.getGroupList(classId, courseId));
//    }

    /**
     * 添加小组
     * @param groupAddRequest
     * @return
     */
    @PostMapping("")
    public Result<Object> addGroup(@RequestBody GroupAddRequestDto groupAddRequest) {
        return ResultUtil.success(groupService.addGroup(groupAddRequest));
    }

    /**
     * 修改小组
     * @return
     */
    @PutMapping("")
    public Result<Object> editGroup(@RequestBody GroupEditRequestDto groupEditRequest) {
        return ResultUtil.success(groupService.editGroup(groupEditRequest));
    }
}
