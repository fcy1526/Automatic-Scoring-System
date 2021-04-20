package com.fcy.scoreservice.entity.dto;

import lombok.Data;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/4/16 23:42
 */
@Data
public class LeaderAndGroupDto {
    /**
     * 小组长id
     */
    private String userId;
    /**
     * 真实姓名
     */
    private String trueName;
    /**
     * 小组id
     */
    private Integer groupId;
}
