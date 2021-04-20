package com.fcy.userservice.entity.dto;

import com.fcy.userservice.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 修改教师视图类
 * @Author: fuchenyang
 * @Date: 2021/3/22 23:45
 */
@Data
public class TeacherUpdateRequestDto extends User {

    /**
     * 教学班级
     */
    private List<Integer> teachClasses;
}
