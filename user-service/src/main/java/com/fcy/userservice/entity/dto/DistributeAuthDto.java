package com.fcy.userservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 分配权限Dto
 * @Author: fuchenyang
 * @Date: 2021/3/26 3:01
 */
@Data
public class DistributeAuthDto {
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 权限id字符串 ‘,’分隔
     */
    private String authIds;
}
