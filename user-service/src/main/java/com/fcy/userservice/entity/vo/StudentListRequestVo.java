package com.fcy.userservice.entity.vo;

import com.fcy.userservice.entity.Role;
import com.fcy.userservice.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 学生列表请求视图类
 * @Author: fuchenyang
 * @Date: 2021/3/18 19:53
 */
@Data
public class StudentListRequestVo extends User {

    /**
     * 性别
     */
    private String sexStr;

    /**
     * 角色列表
     */
    private List<Role> roles;

    /**
     * 角色id列表
     */
    private List<Integer> roleIds;

    /**
     * 班级名称
     */
    private String className;
}
