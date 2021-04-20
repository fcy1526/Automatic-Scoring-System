package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Group;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 小组列表类
 * @Author: fuchenyang
 * @Date: 2021/3/30 13:22
 */
@Data
public class GroupListVo extends Group {
    /**
     * 组长名称
     */
    private String leaderName;
    /**
     * 成员列表
     */
    private List<String> members;
}
