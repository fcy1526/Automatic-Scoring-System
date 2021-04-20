package com.fcy.scoreservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 开启新阶段Dto
 * @Author: fuchenyang
 * @Date: 2021/3/30 22:33
 */
@Data
public class StageStartDto {
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 当前阶段
     */
    private Integer currentStage;
    /**
     * 开始时间
     */
    private String startTime;
}
