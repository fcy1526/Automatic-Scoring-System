package com.fcy.userservice.controller;

import com.fcy.userservice.annotation.AuthCheck;
import com.fcy.userservice.common.BtnAuthName;
import com.fcy.userservice.entity.dto.ClassAddRequestDto;
import com.fcy.userservice.entity.dto.ClassSetTeacherRequestDto;
import com.fcy.userservice.entity.dto.ClassUpdateRequestDto;
import com.fcy.userservice.service.ClassService;
import com.fcy.userservice.service.UserService;
import com.fcy.userservice.util.ResultUtil;
import com.fcy.userservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/18 22:57
 */
@RestController
@RequestMapping("class")
public class ClassController {

    @Autowired
    private ClassService classService;

    /**
     * 获取班级列表
     * @return
     */
    @GetMapping("")
    public Result<Object> getClassList() {
        return ResultUtil.success(classService.getClassList());
    }

    /**
     * 查询班级列表
     * @return
     */
    @GetMapping("query")
    public Result<Object> query(@RequestParam("name") String name,
                                @RequestParam("pagenum") Integer pageNum, @RequestParam("pagesize") Integer pageSize) {
        return ResultUtil.success(classService.query(name, pageNum, pageSize));
    }

    /**
     * 根据教师id获取班级信息
     * @return
     */
    @GetMapping("teacher")
    public Result<Object> getClassListByTeacherId() {
        return ResultUtil.success(classService.getClassListByTeacherId());
    }

    /**
     * 根据classId获取班级信息
     * @param classId
     * @return
     */
    @GetMapping("{classId}")
    public Result<Object> getClassById(@PathVariable("classId") Integer classId) {
        return ResultUtil.success(classService.getClassById(classId));
    }

    /**
     * 获取班级人数
     * @param classId
     * @return
     */
    @GetMapping("count")
    public Result<Object> getTotalCount(@RequestParam("classId") Integer classId) {
        return ResultUtil.success(classService.getTotalCount(classId));
    }

    /**
     * 添加新班级 权限要求：管理员，老师 按钮要求：CLASS_ADD
     * @param addRequest
     * @return
     */
    @PostMapping("")
    @AuthCheck(btnName = BtnAuthName.CLASS_ADD)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> addClass(@RequestBody ClassAddRequestDto addRequest) {
        classService.addClass(addRequest);
        return ResultUtil.success();
    }

    /**
     * 修改班级信息 权限要求：管理员，老师 按钮要求：CLASS_EDIT
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    @AuthCheck(btnName = BtnAuthName.CLASS_EDIT)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> updateClass(@RequestBody ClassUpdateRequestDto updateRequest) {
        classService.updateClass(updateRequest);
        return ResultUtil.success();
    }

    /**
     * 分配教师 权限要求：管理员，教师 按钮要求：CLASS_TEACHER
     * @return
     */
    @PutMapping("teacher")
    @AuthCheck(btnName = BtnAuthName.CLASS_TEACHER)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> setTeacher(@RequestBody ClassSetTeacherRequestDto classSetTeacherRequest) {
        classService.setTeacher(classSetTeacherRequest);
        return ResultUtil.success();
    }

    /**
     * 根据classId删除班级数据 按钮要求：CLASS_DELETE
     * @param classId
     * @return
     */
    @DeleteMapping("{classId}")
    @AuthCheck(btnName = BtnAuthName.CLASS_DELETE)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> deleteClassById(@PathVariable("classId") Integer classId) {
        classService.deleteClassById(classId);
        return ResultUtil.success();
    }

    /**
     * 获取班级名称
     * @param classId
     * @return
     */
    @GetMapping("name/{classId}")
    public Result<Object> getClassName(@PathVariable("classId") Integer classId) {
        return ResultUtil.success(classService.getClassName(classId));
    }
}
