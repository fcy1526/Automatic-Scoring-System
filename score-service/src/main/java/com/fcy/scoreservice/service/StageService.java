package com.fcy.scoreservice.service;

import com.fcy.scoreservice.entity.dto.StageStartDto;
import com.fcy.scoreservice.entity.dto.StageStopDto;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/30 22:10
 */
public interface StageService {
    /**
     * 结束当前阶段
     * @param stageStop
     */
    void stopCurrentStage(StageStopDto stageStop);

    /**
     * 开启新阶段
     * @param stageStart
     */
    void startNewStage(StageStartDto stageStart);
}
