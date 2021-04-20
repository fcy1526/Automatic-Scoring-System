package com.fcy.scoreservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 阶段类
 * @Author: fuchenyang
 * @Date: 2021/3/29 21:35
 */
@Data
public class Stage implements Serializable {

    private static final long serialVersionUID = 3581749485250908810L;
    /**
     * 阶段id
     */
    private Integer stageId;
    /**
     * 阶段次序
     */
    private Integer stageIndex;
    /**
     * 阶段开始时间
     */
    private String startTime;
    /**
     * 阶段结束时间
     */
    private String endTime;
    /**
     * 课程id
     */
    private Integer courseId;

}
