package com.fcy.scoreservice.mapper;

import com.fcy.scoreservice.entity.dto.StageStartDto;
import com.fcy.scoreservice.entity.dto.StageStopDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Describe: 
 * @Author: fuchenyang 
 * @Date: 2021/3/29 21:42
 */
@Mapper
public interface StageMapper {
    void addStage(Integer courseId, Integer stageNum);

    void stopCurrentStage(StageStopDto stageStop);

    void startNewStage(StageStartDto stageStart);

    List<Integer> getStageIds(Integer courseId);

    Integer getStageId(Integer currentStage, Integer courseId);

    Integer getCurrentStageId(Integer courseId);
}
