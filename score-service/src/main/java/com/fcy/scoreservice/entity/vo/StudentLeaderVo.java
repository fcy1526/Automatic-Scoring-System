package com.fcy.scoreservice.entity.vo;

import lombok.Data;

/**
 * @Describe: 小组成员列表视图类
 * @Author: fuchenyang
 * @Date: 2021/4/1 14:40
 */
@Data
public class StudentLeaderVo {
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
    /**
     * 班级id，仅在用户为教师时才用
     */
    private Integer classId;
}
