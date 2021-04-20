package com.fcy.userservice.entity.dto;

import com.fcy.userservice.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 添加教师视图类
 * @Author: fuchenyang
 * @Date: 2021/3/22 20:07
 */
@Data
public class TeacherAddRequestDto extends User {

    /**
     * 教学班级
     */
    private List<Integer> teachClasses;

    /**
     * 角色: 教师
     */
    private Integer roleId;
}
