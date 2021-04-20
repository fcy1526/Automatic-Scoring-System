package com.fcy.userservice.controller;

import com.fcy.userservice.annotation.AuthCheck;
import com.fcy.userservice.common.BtnAuthName;
import com.fcy.userservice.common.Constants;
import com.fcy.userservice.entity.Role;
import com.fcy.userservice.entity.dto.DistributeAuthDto;
import com.fcy.userservice.entity.dto.RoleAddRequestDto;
import com.fcy.userservice.service.RoleService;
import com.fcy.userservice.util.ResultUtil;
import com.fcy.userservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/18 23:36
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     * @return
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> getRoleList() {
        return ResultUtil.success(roleService.getRoleList());
    }

    /**
     * 获取学生角色列表
     * @return
     */
    @GetMapping("student")
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> getStudentRoleList() {
        return ResultUtil.success(roleService.getStudentRoleList());
    }

    /**
     * 获取角色树
     * @return
     */
    @GetMapping("tree")
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> getRoleListTree() {
        return ResultUtil.success(roleService.getRoleListTree());
    }

    /**
     * 根据id获取角色信息
     * @param roleId
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> getRoleById(@PathVariable("id") Integer roleId) {
        return ResultUtil.success(roleService.getRoleById(roleId));
    }

    /**
     * 为角色分配权限 权限要求：管理员 按钮要求：ROLE_AUTH
     * @param authDto
     * @return
     */
    @PostMapping("")
    @AuthCheck(btnName = BtnAuthName.ROLE_AUTH)
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> distributeAuthToRole(@RequestBody DistributeAuthDto authDto) {
        roleService.distributeAuthToRole(authDto);
        return ResultUtil.success();
    }

    /**
     * 新增角色 权限要求：管理员，教师 按钮要求：ROLE_ADD
     * @param roleAddRequest
     * @return
     */
    @PostMapping("add")
    @AuthCheck(btnName = BtnAuthName.ROLE_ADD)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> addRole(@RequestBody RoleAddRequestDto roleAddRequest) {
        roleService.addRole(roleAddRequest);
        // 默认拥有个人中心权限
        DistributeAuthDto authDto = new DistributeAuthDto();
        authDto.setRoleId(roleAddRequest.getId());
        authDto.setAuthIds(Constants.PERSONAL_AUTH_STR);
        roleService.distributeAuthToRole(authDto);
        return ResultUtil.success();
    }

    /**
     * 修改角色信息 权限要求：管理员，教师 (系统角色不可修改) 按钮要求：ROLE_EDIT
     * @param role
     * @return
     */
    @PutMapping("")
    @AuthCheck(btnName = BtnAuthName.ROLE_EDIT)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> updateRole(@RequestBody Role role) {
        // 判断是否为系统角色：普通学生，组长，学习委员，教师，管理员
        if (roleService.checkRoleIsSystem(role.getId())) {
            return ResultUtil.error("该角色为系统角色，不可编辑");
        }
        roleService.update(role);
        return ResultUtil.success();
    }

    /**
     * 删除指定角色的权限
     * @param roleId
     * @param authId
     * @return
     */
    @DeleteMapping("")
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> deleteRoleAuth(@RequestParam("roleId") Integer roleId, @RequestParam("authId") String authId) {
        return ResultUtil.success(roleService.deleteRoleAuth(roleId, authId));
    }

    /**
     * 根据id删除角色 权限要求：管理员，教师 (系统角色不可删除) 按钮要求：ROLE_DELETE
     * @param roleId
     * @return
     */
    @DeleteMapping("{id}")
    @AuthCheck(btnName = BtnAuthName.ROLE_DELETE)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> deleteRoleById(@PathVariable("id") Integer roleId) {
        // 判断是否为系统角色：普通学生，组长，学习委员，教师，管理员
        if (roleService.checkRoleIsSystem(roleId)) {
            return ResultUtil.error("该角色为系统角色，不可删除");
        }
        roleService.deleteRoleById(roleId);
        return ResultUtil.success();
    }
}
