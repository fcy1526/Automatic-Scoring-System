package com.fcy.scoreservice.entity.dto;

import com.fcy.scoreservice.entity.Group;
import lombok.Data;

import java.util.List;

/**
 * @Describe: 修改小组dto
 * @Author: fuchenyang
 * @Date: 2021/3/30 18:18
 */
@Data
public class GroupEditRequestDto extends Group {

    /**
     * 成员列表
     */
    private List<String> members;
}
