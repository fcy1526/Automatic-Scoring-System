package com.fcy.userservice.entity.dto;

import com.fcy.userservice.entity.Role;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Describe: 角色Dto对象，权限数据以tree结构存储
 * @Author: fuchenyang
 * @Date: 2021/3/26 1:04
 */
@Data
public class RoleAuthTreeDto extends Role {
    /**
     * 权限树
     */
    private List<Map<String, Object>> children;
}
