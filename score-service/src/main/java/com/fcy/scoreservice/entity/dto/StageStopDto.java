package com.fcy.scoreservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 阶段中止dto
 * @Author: fuchenyang
 * @Date: 2021/3/30 22:07
 */
@Data
public class StageStopDto {
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 当前阶段
     */
    private Integer currentStage;
    /**
     * 结束时间
     */
    private String endTime;
}
