package com.fcy.userservice.service;

import com.fcy.userservice.entity.Role;
import com.fcy.userservice.entity.dto.DistributeAuthDto;
import com.fcy.userservice.entity.dto.RoleAddRequestDto;
import com.fcy.userservice.entity.dto.RoleAuthTreeDto;
import com.fcy.userservice.entity.dto.UserSetRoleRequestDto;

import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/18 23:37
 */
public interface RoleService {

    /**
     * 获取角色列表
     * @return
     */
    List<Role> getRoleList();

    /**
     * 获取学生角色列表
     * @return
     */
    List<Role> getStudentRoleList();

    /**
     * 分配角色
     * @param userSetRoleRequest
     */
    void setRole(UserSetRoleRequestDto userSetRoleRequest);

    /**
     * 获取除Admin（管理员）角色以外的所有角色树
     * @return
     */
    List<RoleAuthTreeDto> getRoleListTree();

    /**
     * 分配权限
     * @param authDto
     */
    void distributeAuthToRole(DistributeAuthDto authDto);

    /**
     * 删除指定角色的权限
     * @param roleId
     * @param authId
     * @return
     */
    RoleAuthTreeDto deleteRoleAuth(Integer roleId, String authId);

    /**
     * 判断是否为系统角色：普通学生，组长，学习委员，教师，管理员
     * @param roleId
     * @return
     */
    boolean checkRoleIsSystem(Integer roleId);

    /**
     * 修改角色信息
     * @param role
     */
    void update(Role role);

    /**
     * 根据id删除角色 权限要求：管理员，教师 (系统角色不可删除)
     * @param roleId
     */
    void deleteRoleById(Integer roleId);

    /**
     * 根据id获取角色信息
     * @param roleId
     * @return
     */
    Role getRoleById(Integer roleId);

    /**
     * 添加角色
     * @param roleAddRequest
     */
    void addRole(RoleAddRequestDto roleAddRequest);
}
