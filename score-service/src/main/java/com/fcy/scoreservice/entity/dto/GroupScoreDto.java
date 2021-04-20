package com.fcy.scoreservice.entity.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Describe: 小组评分dto
 * @Author: fuchenyang
 * @Date: 2021/4/17 22:45
 */
@Data
public class GroupScoreDto {
    /**
     * 教师评分
     */
    private Integer teacherScore;
    /**
     * 其他小组长评分
     */
    private List<Map<String, Object>> leaderScore;
}
