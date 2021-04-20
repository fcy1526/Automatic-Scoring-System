package com.fcy.userservice.entity.dto;

import com.fcy.userservice.entity.User;
import lombok.Data;

/**
 * @Describe: 新增用户请求dto
 * @Author: fuchenyang
 * @Date: 2021/3/18 23:56
 */
@Data
public class StudentAddRequestDto extends User {

    /**
     * 角色: 普通学生
     */
    private Integer roleId;
}
