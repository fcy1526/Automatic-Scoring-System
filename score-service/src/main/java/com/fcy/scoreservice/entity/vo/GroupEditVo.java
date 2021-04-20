package com.fcy.scoreservice.entity.vo;

import com.fcy.scoreservice.entity.Group;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 修改小组视图类
 * @Author: fuchenyang
 * @Date: 2021/3/30 17:57
 */
@Data
public class GroupEditVo extends Group {

    /**
     * 成员列表
     */
    private List<UserGroupVo> memberList;
    /**
     * 成员id列表
     */
    private List<String> members;
}
