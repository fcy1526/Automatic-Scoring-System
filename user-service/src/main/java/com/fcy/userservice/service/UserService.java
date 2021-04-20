package com.fcy.userservice.service;


import com.fcy.userservice.entity.User;
import com.fcy.userservice.entity.dto.*;
import com.fcy.userservice.entity.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


/**
 * @Describe:
 * @Author: fuchenyang 
 * @Date: 2021/2/14 20:12
 */
public interface UserService {

    /**
     * 根据学号查询用户信息
     * @param userid
     * @return
     */
    User findByUserid(String userid);

    /**
     * 修改用户基本信息
     * @param user
     */
    void update(UserUpdateRequestDto user);

    /**
     * 修改学生信息
     * @param student
     */
    void update(StudentUpdateRequestDto student);

    /**
     * 修改教师信息
     * @param teacherUpdateRequest
     */
    void update(TeacherUpdateRequestDto teacherUpdateRequest);

    /**
     * 修改密码
     * @param passwordDto
     */
    void update(PasswordUpdateRequestDto passwordDto);

    /**
     * 获取学生信息列表
     * @return
     */
    PageInfo<StudentListRequestVo> getStudentList(Map map);

    /**
     * 添加新学生
     * @param user
     */
    void addStudent(StudentAddRequestDto user);

    /**
     * 根据学号获取学生信息
     * @param userId
     * @return
     */
    StudentUpdateVo getStudentById(String userId);

    /**
     * 根据id删除用户
     * @param userId
     */
    void deleteByUserId(String userId);

    /**
     * 获取教师信息列表
     * @param map
     * @return
     */
    PageInfo<TeacherListRequestVo> getTeacherList(Map map);

    /**
     * 获取教师基本信息
     * @return
     */
    List<ClassGetTeacherListVo> getTeacherList();

    /**
     * 添加教师
     * @param teacherAddRequest
     */
    void addTeacher(TeacherAddRequestDto teacherAddRequest);

    /**
     * 根据id获取教师信息
     * @param userId
     * @return
     */
    TeacherUpdateVo getTeacherById(String userId);
}
