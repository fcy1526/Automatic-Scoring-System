package com.fcy.userservice.controller;

import com.fcy.userservice.annotation.AuthCheck;
import com.fcy.userservice.common.BtnAuthName;
import com.fcy.userservice.entity.dto.StudentAddRequestDto;
import com.fcy.userservice.entity.dto.StudentUpdateRequestDto;
import com.fcy.userservice.entity.dto.UserGetListRequestDto;
import com.fcy.userservice.enums.RoleEnum;
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
 * @Date: 2021/3/20 21:49
 */
@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有学生列表 权限要求：管理员、老师
     * @param query 查询条件
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("query")
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> getStudentList(UserGetListRequestDto query,
                                      @RequestParam("pagenum") String pageNum, @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("query", query);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(userService.getStudentList(map));
    }

    /**
     * 添加新学生 权限要求：管理员，老师 按钮要求：STUDENT_ADD
     * @param user
     * @return
     */
    @PostMapping("")
    @AuthCheck(btnName = BtnAuthName.STUDENT_ADD)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> addStudent(@RequestBody StudentAddRequestDto user) {
        // 角色为普通学生
        user.setRoleId(RoleEnum.STUDENT.getId());
        userService.addStudent(user);
        return ResultUtil.success();
    }

    /**
     * 根据学号获取学生信息
     * @param userId
     * @return
     */
    @GetMapping("{userId}")
    public Result<Object> getStudentInfoById(@PathVariable("userId") String userId) {
        return ResultUtil.success(userService.getStudentById(userId));
    }

    /**
     * 修改学生信息 权限要求：管理员，老师 按钮要求：STUDENT_EDIT
     * @return
     */
    @PutMapping("")
    @AuthCheck(btnName = BtnAuthName.STUDENT_EDIT)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> updateStudent(@RequestBody StudentUpdateRequestDto user) {
        userService.update(user);
        return ResultUtil.success();
    }

}
