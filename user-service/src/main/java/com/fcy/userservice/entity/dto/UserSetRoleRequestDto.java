package com.fcy.userservice.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @Describe: 分配角色请求Dto
 * @Author: fuchenyang
 * @Date: 2021/3/21 18:11
 */
@Data
public class UserSetRoleRequestDto {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色列表
     */
    private List<Integer> roles;
}
