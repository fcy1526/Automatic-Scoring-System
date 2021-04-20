package com.fcy.scoreservice.entity;

import lombok.Data;

/**
 * @Describe: 小组平均分
 * @Author: fuchenyang
 * @Date: 2021/4/19 13:21
 */
@Data
public class AvgGroupScore {
    /**
     * 小组id
     */
    private Integer groupId;
    /**
     * 阶段id
     */
    private Integer stageId;
    /**
     * 平均分
     */
    private Integer score;
}
