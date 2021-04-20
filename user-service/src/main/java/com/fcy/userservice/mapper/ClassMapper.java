package com.fcy.userservice.mapper;

import com.fcy.userservice.entity.Class;
import com.fcy.userservice.entity.User;
import com.fcy.userservice.entity.dto.ClassAddRequestDto;
import com.fcy.userservice.entity.dto.ClassSetTeacherRequestDto;
import com.fcy.userservice.entity.dto.ClassUpdateRequestDto;
import com.fcy.userservice.entity.vo.ClassListVo;
import com.fcy.userservice.entity.vo.ClassStudentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Describe: 
 * @Author: fuchenyang 
 * @Date: 2021/3/18 23:00
 */
@Mapper
public interface ClassMapper {

    List<Class> getClassList();

    void addList(String userId, List<Integer> teachClasses);

    List<String> getClassesById(String userId);

    void delete(String userId);

    List<ClassListVo> query(String name);

    void addClass(ClassAddRequestDto addRequest);

    Class getClassById(Integer classId);

    void updateClass(ClassUpdateRequestDto updateRequest);

    void deleteClassById(Integer classId);

    List<User> getTeacherById(Integer classId);

    List<ClassStudentVo> getStudentById(Integer classId);

    void deleteTeacher(Integer classId);

    void setTeacher(Integer classId, List<String> teacherIdList);

    List<Class> getClassListByTeacherId(String teacherId);

    Integer getTotalCount(Integer classId);
}
