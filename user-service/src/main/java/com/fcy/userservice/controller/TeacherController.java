package com.fcy.userservice.controller;

import com.fcy.userservice.annotation.AuthCheck;
import com.fcy.userservice.common.BtnAuthName;
import com.fcy.userservice.entity.dto.TeacherAddRequestDto;
import com.fcy.userservice.entity.dto.TeacherUpdateRequestDto;
import com.fcy.userservice.entity.dto.UserGetListRequestDto;
import com.fcy.userservice.enums.RoleEnum;
import com.fcy.userservice.service.ClassService;
import com.fcy.userservice.service.UserService;
import com.fcy.userservice.util.ResultUtil;
import com.fcy.userservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/21 23:37
 */
@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    /**
     * 获取所有教师列表 权限要求：管理员
     * @param query 查询条件
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> getTeacherList(UserGetListRequestDto query,
                                         @RequestParam("pagenum") String pageNum, @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("query", query);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(userService.getTeacherList(map));
    }

    /**
     * 获取所有教师基本信息
     * @return
     */
    @GetMapping("list")
    public Result<Object> getTeacherList() {
        return ResultUtil.success(userService.getTeacherList());
    }

    /**
     * 根据userid获取教师信息 权限要求：管理员
     * @param userId
     * @return
     */
    @GetMapping("{userId}")
    public Result<Object> getTeacherByUserId(@PathVariable("userId") String userId) {
        return ResultUtil.success(userService.getTeacherById(userId));
    }

    /**
     * 添加新教师 权限要求：管理员 按钮要求：TEACHER_ADD
     * @param teacherAddRequest
     * @return
     */
    @PostMapping("")
    @AuthCheck(btnName = BtnAuthName.TEACHER_ADD)
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> addTeacher(@RequestBody TeacherAddRequestDto teacherAddRequest) {
        // 角色为老师
        teacherAddRequest.setRoleId(RoleEnum.TEACHER.getId());
        userService.addTeacher(teacherAddRequest);
        return ResultUtil.success();
    }

    /**
     * 修改教师信息 权限要求：管理员 按钮要求：TEACHER_EDIT
     * @param teacherUpdateRequest
     * @return
     */
    @PutMapping("")
    @AuthCheck(btnName = BtnAuthName.TEACHER_EDIT)
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> updateTeacher(@RequestBody TeacherUpdateRequestDto teacherUpdateRequest) {
        userService.update(teacherUpdateRequest);
        return ResultUtil.success();
    }

    /**
     * 根据id删除教师 权限要求：管理员 按钮要求：TEACHER_DELETE
     * @param userId
     * @return
     */
    @DeleteMapping("{userId}")
    @AuthCheck(btnName = BtnAuthName.TEACHER_DELETE)
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> deleteTeacher(@PathVariable("userId") String userId) {
        // 删除用户 关联角色
        userService.deleteByUserId(userId);
        // 删除关联班级
        classService.delete(userId);
        return ResultUtil.success();
    }
}
