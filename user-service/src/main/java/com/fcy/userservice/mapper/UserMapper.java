package com.fcy.userservice.mapper;

import com.fcy.userservice.entity.Class;
import com.fcy.userservice.entity.Role;
import com.fcy.userservice.entity.User;
import com.fcy.userservice.entity.dto.*;
import com.fcy.userservice.entity.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/2/14 20:15
 */
@Mapper
public interface UserMapper {

    User findByUserid(String userId);

    void update(User user);

    void updateStudent(StudentUpdateRequestDto user);

    void updatePwd(PasswordUpdateRequestDto passwordDto);

    List<StudentListRequestVo> getStudentList(Map<String, Object> query);

    List<Role> getRoleByUserId(String userId);

    void addUser(User user);

    StudentUpdateVo getStudentById(String userId);

    TeacherUpdateVo getTeacherById(String userId);

    void deleteByUserId(String userId);

    List<TeacherListRequestVo> getTeacherList(Map<String, Object> query);

    List<ClassGetTeacherListVo> getTeacherSimpleList();

    List<Class> getClassByUserId(String userId);

    void deleteClassId(Integer classId);
}
