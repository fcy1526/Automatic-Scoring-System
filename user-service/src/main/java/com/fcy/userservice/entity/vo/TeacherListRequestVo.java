package com.fcy.userservice.entity.vo;

import com.fcy.userservice.entity.Class;
import com.fcy.userservice.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 教师列表视图类
 * @Author: fuchenyang
 * @Date: 2021/3/22 13:50
 */
@Data
public class TeacherListRequestVo extends User {

    /**
     * 性别
     */
    private String sexStr;

    /**
     * 教学班级
     */
    private List<Class> teachClasses;
}
