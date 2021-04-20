package com.fcy.scoreservice.entity.vo;

import lombok.Data;

/**
 * @Describe: 学生表现评分列表视图类
 * @Author: fuchenyang
 * @Date: 2021/3/31 15:44
 */
@Data
public class StudentScoreListVo {
    /**
     * 学生id
     */
    private String userId;
    /**
     * 学生姓名
     */
    private String trueName;
    /**
     * 成绩
     */
    private Integer score;

}
