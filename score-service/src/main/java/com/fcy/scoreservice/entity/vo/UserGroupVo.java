package com.fcy.scoreservice.entity.vo;

import lombok.Data;

/**
 * @Describe: 小组学生视图类
 * @Author: fuchenyang
 * @Date: 2021/3/30 19:36
 */
@Data
public class UserGroupVo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 真实姓名
     */
    private String trueName;
}
