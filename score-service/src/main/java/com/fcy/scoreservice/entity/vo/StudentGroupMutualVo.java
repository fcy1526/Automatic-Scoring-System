package com.fcy.scoreservice.entity.vo;

import lombok.Data;

/**
 * @Describe: 小组互评成绩视图类
 * @Author: fuchenyang
 * @Date: 2021/3/31 23:21
 */
@Data
public class StudentGroupMutualVo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 真实姓名
     */
    private String trueName;
    /**
     * 成绩
     */
    private Integer score;
}
