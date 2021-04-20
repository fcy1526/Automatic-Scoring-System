package com.fcy.userservice.service;

import com.fcy.userservice.entity.Class;
import com.fcy.userservice.entity.dto.ClassAddRequestDto;
import com.fcy.userservice.entity.dto.ClassSetTeacherRequestDto;
import com.fcy.userservice.entity.dto.ClassUpdateRequestDto;
import com.fcy.userservice.entity.vo.ClassListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/18 22:58
 */
public interface ClassService {
    /**
     * 获取班级列表
     * @return
     */
    List<Class> getClassList();

    /**
     * 删除班级关联
     * @param userId
     */
    void delete(String userId);

    /**
     * 根据班级名称查询班级列表
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ClassListVo> query(String name, Integer pageNum, Integer pageSize);

    /**
     * 添加新班级
     * @param addRequest
     */
    void addClass(ClassAddRequestDto addRequest);

    /**
     * 根据classId获取班级信息
     * @param classId
     * @return
     */
    Class getClassById(Integer classId);

    /**
     * 修改班级信息
     * @param updateRequest
     */
    void updateClass(ClassUpdateRequestDto updateRequest);

    /**
     * 根据classId删除班级
     * @param classId
     */
    void deleteClassById(Integer classId);

    /**
     * 分配教师
     * @param classSetTeacherRequest
     */
    void setTeacher(ClassSetTeacherRequestDto classSetTeacherRequest);

    /**
     * 根据教师id获取班级信息
     * @return
     */
    List<Class> getClassListByTeacherId();

    /**
     * 根据classId获取班级人数
     * @param classId
     * @return
     */
    Integer getTotalCount(Integer classId);
}
