package com.fcy.scoreservice.entity.vo;

import lombok.Data;

/**
 * @Describe: 小组长视图类
 * @Author: fuchenyang
 * @Date: 2021/4/1 20:41
 */
@Data
public class LeaderVo {
    /**
     * 小组长id
     */
    private String userId;
    /**
     * 真实姓名
     */
    private String trueName;

}
