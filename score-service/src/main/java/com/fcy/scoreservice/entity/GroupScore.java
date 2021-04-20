package com.fcy.scoreservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @Describe: 小组评分实体类
 * @Author: fuchenyang 
 * @Date: 2021/3/30 10:56
 */
@Data
@Builder
public class GroupScore implements Serializable {

    @Tolerate
    public GroupScore() {
    }

    private static final long serialVersionUID = -3496659372263211999L;
    /**
     * id
     */
    private Integer id;
    /**
     * 评分人id
     */
    private String scoreId;
    /**
     * 评分人真实姓名
     */
    private String trueName;
    /**
     * 评分人类型：教师，其他小组长
     */
    private String type;
    /**
     * 阶段id
     */
    private Integer stageId;
    /**
     * 小组id
     */
    private Integer groupId;
    /**
     * 成绩
     */
    private Integer score;
}
