package com.fcy.scoreservice.mapper;

import com.fcy.scoreservice.entity.User;
import com.fcy.scoreservice.entity.vo.UserGroupVo;
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

    /**
     * 获取未分组的学生
     * @param classId
     * @param courseId
     * @return
     */
    List<UserGroupVo> getStudentList(Integer classId, Integer courseId);

    /**
     * 获取学生id列表
     * @param classIds
     * @return
     */
    List<Map<String, String>> getStudentIds(List<Integer> classIds);

    /**
     * 根据班级id获取学生信息
     * @param classId
     * @return
     */
    List<UserGroupVo> getAllStudentList(Integer classId);

    /**
     * 根据课程id获取所有学生id
     * @param courseId
     * @return
     */
    List<String> getStudentListByCourseId(Integer courseId);

    /**
     * 获取班级学生人数
     * @param classId
     * @return
     */
    Integer getStudentCount(Integer classId);

}
