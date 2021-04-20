package com.fcy.scoreservice.entity.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Describe: 小组评分详细视图类
 * @Author: fuchenyang
 * @Date: 2021/4/15 23:33
 */
@Data
public class GroupScoreVo {

    /**
     * 教师评分
     */
    private Integer teacherScore;
    /**
     * 其他小组长评分
     */
    private List<Map<String, Object>> leaderScore;

}
