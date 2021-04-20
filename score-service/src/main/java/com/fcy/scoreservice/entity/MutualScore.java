package com.fcy.scoreservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 小组互评实体类
 * @Author: fuchenyang
 * @Date: 2021/3/30 10:58
 */
@Data
public class MutualScore implements Serializable {
    private static final long serialVersionUID = 7712606871939639203L;
    /**
     * id
     */
    private Integer id;
    /**
     * 评分人id
     */
    private String scoreId;
    /**
     * 被评分人id
     */
    private String targetId;
    /**
     * 小组id
     */
    private Integer groupId;
    /**
     * 阶段id
     */
    private Integer stageId;
    /**
     * 成绩
     */
    private Integer score;
}
