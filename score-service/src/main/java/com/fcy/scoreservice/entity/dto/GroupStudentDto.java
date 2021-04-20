package com.fcy.scoreservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 小组成员映射dto
 * @Author: fuchenyang
 * @Date: 2021/4/19 19:55
 */
@Data
public class GroupStudentDto {
    /**
     * 小组id
     */
    private Integer groupId;
    /**
     * 用户id
     */
    private String userId;
}
