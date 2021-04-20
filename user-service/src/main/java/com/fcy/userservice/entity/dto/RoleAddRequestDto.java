package com.fcy.userservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 新增角色Dto
 * @Author: fuchenyang
 * @Date: 2021/3/26 19:09
 */
@Data
public class RoleAddRequestDto {
    /**
     * 角色id 由数据库自增后，mybatis自动回填
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String describe;
    /**
     * 是否为学生角色
     */
    private Integer isStudentRole;
    /**
     * 是否为系统角色
     */
    Integer isSysRole;

    @Override
    public String toString() {
        return "新增角色{" +
                "角色名称: '" + name + '\'' +
                ", 角色描述: '" + describe + '\'' +
                ", 是否为学生角色: " + isStudentRole +
                '}';
    }
}
