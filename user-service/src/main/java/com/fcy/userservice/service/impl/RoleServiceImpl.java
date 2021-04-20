package com.fcy.userservice.service.impl;

import com.fcy.userservice.common.Constants;
import com.fcy.userservice.entity.Role;
import com.fcy.userservice.entity.SysLog;
import com.fcy.userservice.entity.dto.DistributeAuthDto;
import com.fcy.userservice.entity.dto.RoleAddRequestDto;
import com.fcy.userservice.entity.dto.RoleAuthTreeDto;
import com.fcy.userservice.entity.dto.UserSetRoleRequestDto;
import com.fcy.userservice.enums.OperationEnum;
import com.fcy.userservice.enums.RoleEnum;
import com.fcy.userservice.mapper.RoleMapper;
import com.fcy.userservice.service.AuthService;
import com.fcy.userservice.service.LogService;
import com.fcy.userservice.service.RoleService;
import com.fcy.userservice.util.AuthUtil;
import com.fcy.userservice.util.SystemTable;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fcy.userservice.common.Constants.CHANGE_LOG_TEMPLATE;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/18 23:43
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private LogService logService;

    @Autowired
    private AuthService authService;

    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public List<Role> getStudentRoleList() {
        return roleMapper.getStudentRoleList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRole(UserSetRoleRequestDto userSetRoleRequest) {
        String userId = userSetRoleRequest.getUserId();
        List<String> roleList = roleMapper.getRoleListById(userId);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_USER_ROLE)
                        .operation("delete user_role")
                        .build());
        // 删除已有角色
        roleMapper.deleteByUserId(userId);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_USER_ROLE)
                        .operation(getChangeLog("角色", roleList, userSetRoleRequest.getRoles()))
                        .build());
//        // 将角色名称转换为角色id
//        List<Integer> roles = userSetRoleRequest.getRoles().stream().
//                map(RoleEnum::getId).collect(Collectors.toList());

        // 插入角色
        roleMapper.addList(userId, userSetRoleRequest.getRoles());
    }

    @Override
    public List<RoleAuthTreeDto> getRoleListTree() {
        List<Role> roleList = roleMapper.getRoleList();
        List<RoleAuthTreeDto> roleAuthTreeList = new ArrayList<>();
        for (Role role : roleList) {
            RoleAuthTreeDto roleAuthTree = new RoleAuthTreeDto();
            BeanUtils.copyProperties(role, roleAuthTree);
            // 将authList转换为authTree
            roleAuthTree.setChildren(AuthUtil.convertAuthorityListToTreeList(role.getAuthorities()));
            roleAuthTreeList.add(roleAuthTree);
        }
        return roleAuthTreeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void distributeAuthToRole(DistributeAuthDto authDto) {
        Integer roleId = authDto.getRoleId(); // 角色id
        String authIds = authDto.getAuthIds(); // 权限id列表
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE) // 清空操作
                        .opertable(SystemTable.T_ROLE_AUTH) // 用户角色关联表
                        .operation("delete role_auth, roleId: [" + roleId + "]")
                        .build());
        // 分配前先清空该角色下的所有权限
        roleMapper.clearAuths(roleId);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT) // 插入操作
                        .opertable(SystemTable.T_ROLE_AUTH) // 用户角色关联表
                        .operation("roleId: [" + roleId + "], authIds: [" + authIds + "]")
                        .build());
        // 再分配权限
        roleMapper.distributeAuthToRole(roleId, authIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleAuthTreeDto deleteRoleAuth(Integer roleId, String authId) {
        List<String> authIds = new ArrayList<>();
        authIds.add(authId);
        // 获取该权限下的子权限id
        // 追加子节点
        authIds.addAll(authService.getIdsByParentIds(Lists.newArrayList(authId)));
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_ROLE_AUTH)
                        .operation("roleId: [" + roleId + "], authIds: [" + authIds + "]")
                        .build());
        roleMapper.deleteRoleAuth(roleId, authIds);
        // 重新获取该角色的权限
        Role role = roleMapper.getRoleById(roleId);
        RoleAuthTreeDto roleAuthTree = new RoleAuthTreeDto();
        BeanUtils.copyProperties(role, roleAuthTree);
        // 将authList转换为authTree
        roleAuthTree.setChildren(AuthUtil.convertAuthorityListToTreeList(role.getAuthorities()));
        return roleAuthTree;
    }

    @Override
    public boolean checkRoleIsSystem(Integer roleId) {
        return roleMapper.checkRoleIsSystem(roleId) == Constants.SYS_ROLE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role role) {
        Role oldRole = roleMapper.getRoleById(role.getId());
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_ROLE)
                        .operation("角色ID: " + role.getId() + ";" + role.compare(oldRole))
                        .build());
        roleMapper.update(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleById(Integer roleId) {
        Role role = roleMapper.getRoleById(roleId);
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_ROLE)
                        .operation(role.toString())
                        .build());
        roleMapper.deleteRoleById(roleId);
    }

    @Override
    public Role getRoleById(Integer roleId) {
        return roleMapper.getRoleById(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(RoleAddRequestDto roleAddRequest) {
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_ROLE)
                        .operation(roleAddRequest.toString())
                        .build());
        if (roleAddRequest.getIsStudentRole() == Constants.IS_STUDENT_ROLE) {
            // 为学生角色，为name增加Student_前缀
            roleAddRequest.setName(Constants.STUDENT_PRE + roleAddRequest.getName());
        }
        // 设置为非系统角色
        roleAddRequest.setIsSysRole(Constants.NOT_SYS_ROLE);
        roleMapper.addRole(roleAddRequest);
    }

    /**
     * 获取字段变更记录
     * @param field
     * @param oldVal
     * @param newVal
     * @return
     */
    public String getChangeLog(String field, Object oldVal, Object newVal) {
        return String.format(CHANGE_LOG_TEMPLATE, field, oldVal, newVal);
    }
}
