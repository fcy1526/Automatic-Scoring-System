package com.fcy.scoreservice.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @Describe: 小组dto
 * @Author: fuchenyang
 * @Date: 2021/3/31 20:03
 */
@Data
public class GroupDto {
    /**
     * 小组id
     */
    private Integer groupId;
    /**
     * 小组长
     */
    private String leaderId;
    /**
     * 小组成员
     */
    private List<String> members;
    /**
     * 小组人数
     */
    private Integer count;

    /**
     * 计算小组人数
     */
    public void calCount() {
        this.count = members.size();
    }

}
